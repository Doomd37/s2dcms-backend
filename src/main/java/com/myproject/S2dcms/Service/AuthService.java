package com.myproject.S2dcms.Service;

import com.myproject.S2dcms.Exception.*;
import com.myproject.S2dcms.dto.auth.AuthResponse;
import com.myproject.S2dcms.dto.auth.ChangePasswordRequest;
import com.myproject.S2dcms.dto.auth.LoginRequest;
import com.myproject.S2dcms.dto.auth.RefreshTokenRequest;
import com.myproject.S2dcms.dto.verification.ForgotPasswordRequest;
import com.myproject.S2dcms.dto.verification.ResetPasswordRequest;
import com.myproject.S2dcms.model.Department;
import com.myproject.S2dcms.model.RefreshToken;
import com.myproject.S2dcms.model.Role;
import com.myproject.S2dcms.model.Student;
import com.myproject.S2dcms.repository.DepartmentRepo;
import com.myproject.S2dcms.repository.RefreshTokenRepository;
import com.myproject.S2dcms.repository.StudentRepo;
import com.myproject.S2dcms.securityConfig.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sendinblue.ApiException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService{

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService tokenService;
    private final JwtUtil jwtUtil;
    private final StudentRepo studentRepository;
    private final DepartmentRepo departmentRepository;
    private final UserActionService userActionService;
    private final PasswordEncoder passwordEncoder;
    private final TokenLimitService tokenLimitService;
    private final MailService mailService;


    public AuthService(RefreshTokenRepository refreshTokenRepository, RefreshTokenService tokenService, JwtUtil jwtUtil, StudentRepo studentRepository, DepartmentRepo departmentRepository, UserActionService userActionService, PasswordEncoder passwordEncoder, TokenLimitService tokenLimitService, MailService mailService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.userActionService = userActionService;
        this.passwordEncoder = passwordEncoder;
        this.tokenLimitService = tokenLimitService;
        this.mailService = mailService;
    }

    public AuthResponse login(LoginRequest dto) {

        String email = dto.getEmail();

        Student student = studentRepository.findByEmailIgnoreCase(email).orElse(null);
        Department department = null;

        String accessToken;
        RefreshToken refreshToken;

        if (student != null) {

            userActionService.checkRateLimit(dto.getEmail(), "STUDENT_LOGIN");

            if (!passwordEncoder.matches(dto.getPassword(), student.getPassword())) {
                throw new InvalidPasswordException("Invalid credentials");
            }

            if (!student.isEmailVerified()) {
                throw new EmailVerificationException("Email not verified");
            }

            tokenLimitService.manageTokenLimitForStudent(student);

            accessToken = jwtUtil.generateToken(student.getEmail(), student.getRole());
            refreshToken = tokenService.createRefreshTokenForStudent(student);
        }

        else {

            userActionService.checkRateLimit(dto.getEmail(), "DEPARTMENT_LOGIN");

            department = departmentRepository.findByEmailIgnoreCase(email)
                    .orElseThrow(() -> new InvalidPasswordException("Invalid credentials"));

            if (!passwordEncoder.matches(dto.getPassword(), department.getPassword())) {
                throw new InvalidPasswordException("Invalid credentials");
            }

            tokenLimitService.manageTokenLimitForDepartment(department);

            accessToken = jwtUtil.generateToken(department.getEmail(), department.getRole());
            refreshToken = tokenService.createRefreshTokenForDepartment(department);
        }

        return new AuthResponse(accessToken, refreshToken.getToken());
    }


    public AuthResponse refreshToken(RefreshTokenRequest request) {

    RefreshToken oldToken = refreshTokenRepository.findByToken(request.getRefreshToken())
            .orElseThrow(() -> new RefreshTokenException("Invalid refresh token"));

    if (oldToken.isRevoked()) {
        throw new RefreshTokenException("Refresh token revoked");
    }

    if (oldToken.getExpiryDate().isBefore(Instant.now())) {
        throw new RefreshTokenException("Refresh token expired");
    }

    /*  DETECT USER TYPE */
    String email;
    Role role;

    if (oldToken.getStudent() != null) {
        email = oldToken.getStudent().getEmail();
        role = oldToken.getStudent().getRole();
    } else if (oldToken.getDepartment() != null) {
        email = oldToken.getDepartment().getEmail();
        role = oldToken.getDepartment().getRole();
    } else {
        throw new RefreshTokenException("Invalid token owner");
    }

    /* ROTATE TOKEN */
    RefreshToken newToken = tokenService.rotateToken(oldToken);

    /* GENERATE NEW ACCESS TOKEN */
    String newAccessToken = jwtUtil.generateToken(email, role);

    return new AuthResponse(
            newAccessToken,
            newToken.getToken()
    );
    }

    public void forgotPassword(ForgotPasswordRequest request) {

        userActionService.checkRateLimit(request.getEmail(), "FORGOT_PASSWORD");

        String token = UUID.randomUUID().toString();

        Student student = studentRepository.findByEmailIgnoreCase(request.getEmail()).orElse(null);

        if (student != null) {

            student.setPasswordResetToken(token);
            student.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24));
            studentRepository.save(student);

            try {
                mailService.sendPasswordResetEmail(student.getEmail(), token);
            } catch (ApiException e) {
                throw new ResendVerificationException("Failed to send password reset email");
            }
            return;
        }

        Department department = departmentRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));

        department.setPasswordResetToken(token);
        department.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24));
        departmentRepository.save(department);

        try {
            mailService.sendDepartmentPasswordResetEmail(department.getEmail(), token);
        } catch (ApiException e) {
            throw new ResendVerificationException("Failed to send password reset email");
        }
    }

    public void resetPassword(ResetPasswordRequest request) {

        String token = request.getToken();

        /* TRY STUDENT FIRST */
        Optional<Student> studentOpt =
                studentRepository.findByPasswordResetToken(token);

        if (studentOpt.isPresent()) {

            Student student = studentOpt.get();

            if (student.getPasswordResetTokenExpiry() == null ||
                    student.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
                throw new RefreshTokenException("Token expired");
            }

            student.setPassword(passwordEncoder.encode(request.getNewPassword()));
            student.setPasswordResetToken(null);
            student.setPasswordResetTokenExpiry(null);
            studentRepository.save(student);

            return;
        }

        /* THEN TRY DEPARTMENT */
        Optional<Department> deptOpt =
                departmentRepository.findByPasswordResetToken(token);

        if (deptOpt.isPresent()) {

            Department department = deptOpt.get();

            if (department.getPasswordResetTokenExpiry() == null ||
                    department.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
                throw new RefreshTokenException("Token expired");
            }

            department.setPassword(passwordEncoder.encode(request.getNewPassword()));
            department.setPasswordResetToken(null);
            department.setPasswordResetTokenExpiry(null);
            departmentRepository.save(department);

            return;
        }

        throw new RefreshTokenException("Invalid token");
    }



    public void changePassword(String email, ChangePasswordRequest request) {

        Student student = studentRepository.findByEmailIgnoreCase(email).orElse(null);

        if (student != null) {

            if (!passwordEncoder.matches(request.getOldPassword(), student.getPassword())) {
                throw new InvalidPasswordException("Old password is incorrect");
            }

            student.setPassword(passwordEncoder.encode(request.getNewPassword()));
            studentRepository.save(student);

            refreshTokenRepository.deleteByStudent(student);
            return;
        }

        Department department = departmentRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), department.getPassword())) {
            throw new InvalidPasswordException("Old password is incorrect");
        }

        department.setPassword(passwordEncoder.encode(request.getNewPassword()));
        departmentRepository.save(department);

        refreshTokenRepository.deleteByDepartment(department);
    }



    public void logout(RefreshTokenRequest request) {

        refreshTokenRepository.findByToken(request.getRefreshToken())
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });
    }

}