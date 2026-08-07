package com.myproject.S2dcms.Service;

import com.myproject.S2dcms.Exception.*;
import com.myproject.S2dcms.dto.auth.AuthResponse;
import com.myproject.S2dcms.dto.auth.ChangePasswordRequest;
import com.myproject.S2dcms.dto.auth.LoginRequest;
import com.myproject.S2dcms.dto.auth.RefreshTokenRequest;
import com.myproject.S2dcms.dto.email.EmailMessage;
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
    private final EmailProducerService emailProducerService;
    private final UserLookupService userLookupService;


    public AuthService(RefreshTokenRepository refreshTokenRepository, RefreshTokenService tokenService, JwtUtil jwtUtil, StudentRepo studentRepository, DepartmentRepo departmentRepository, UserActionService userActionService, PasswordEncoder passwordEncoder, TokenLimitService tokenLimitService, EmailProducerService emailProducerService, UserLookupService userLookupService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.userActionService = userActionService;
        this.passwordEncoder = passwordEncoder;
        this.tokenLimitService = tokenLimitService;
        this.emailProducerService = emailProducerService;
        this.userLookupService = userLookupService;
    }

    public AuthResponse login(LoginRequest dto) {

        String email = dto.getEmail();
        UserLookupService.UserResult userResult = userLookupService.findByEmail(email);

        if (userResult == null) {
            throw new InvalidPasswordException("Invalid credentials");
        }

        String rateLimitAction = userResult.userType() == UserLookupService.UserType.STUDENT 
            ? "STUDENT_LOGIN" 
            : "DEPARTMENT_LOGIN";

        userActionService.checkRateLimit(email, rateLimitAction);

        if (!passwordEncoder.matches(dto.getPassword(), userResult.getPassword())) {
            throw new InvalidPasswordException("Invalid credentials");
        }

        if (userResult.userType() == UserLookupService.UserType.STUDENT && !userResult.getStudent().isEmailVerified()) {
            throw new EmailVerificationException("Email not verified");
        }

        userActionService.resetRateLimit(email, rateLimitAction);

        String accessToken;
        RefreshToken refreshToken;

        if (userResult.userType() == UserLookupService.UserType.STUDENT) {
            tokenLimitService.manageTokenLimitForStudent(userResult.getStudent());
            accessToken = jwtUtil.generateToken(userResult.getEmail(), userResult.getRole());
            refreshToken = tokenService.createRefreshTokenForStudent(userResult.getStudent());
        } else {
            tokenLimitService.manageTokenLimitForDepartment(userResult.getDepartment());
            accessToken = jwtUtil.generateToken(userResult.getEmail(), userResult.getRole());
            refreshToken = tokenService.createRefreshTokenForDepartment(userResult.getDepartment());
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
        UserLookupService.UserResult userResult = userLookupService.findByEmail(request.getEmail());

        if (userResult == null) {
            throw new DepartmentNotFoundException("User not found");
        }

        if (userResult.userType() == UserLookupService.UserType.STUDENT) {
            Student student = userResult.getStudent();
            student.setPasswordResetToken(token);
            student.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24));
            studentRepository.save(student);

            EmailMessage emailMessage = new EmailMessage(
                student.getEmail(),
                "Reset your password",
                "PASSWORD_RESET_STUDENT",
                token,
                student.getName()
            );
            emailProducerService.sendEmailMessage(emailMessage);
        } else {
            Department department = userResult.getDepartment();
            department.setPasswordResetToken(token);
            department.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24));
            departmentRepository.save(department);

            EmailMessage emailMessage = new EmailMessage(
                department.getEmail(),
                "Reset your password",
                "PASSWORD_RESET_DEPARTMENT",
                token,
                department.getDepartmentName()
            );
            emailProducerService.sendEmailMessage(emailMessage);
        }
    }

    public void resetPassword(ResetPasswordRequest request) {

        String token = request.getToken();
        UserLookupService.UserResult userResult = userLookupService.findByPasswordResetToken(token);

        if (userResult == null) {
            throw new RefreshTokenException("Invalid token");
        }

        if (userResult.userType() == UserLookupService.UserType.STUDENT) {
            Student student = userResult.getStudent();

            if (student.getPasswordResetTokenExpiry() == null ||
                    student.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
                throw new RefreshTokenException("Token expired");
            }

            student.setPassword(passwordEncoder.encode(request.getNewPassword()));
            student.setPasswordResetToken(null);
            student.setPasswordResetTokenExpiry(null);
            studentRepository.save(student);
        } else {
            Department department = userResult.getDepartment();

            if (department.getPasswordResetTokenExpiry() == null ||
                    department.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
                throw new RefreshTokenException("Token expired");
            }

            department.setPassword(passwordEncoder.encode(request.getNewPassword()));
            department.setPasswordResetToken(null);
            department.setPasswordResetTokenExpiry(null);
            departmentRepository.save(department);
        }
    }



    public void changePassword(String email, ChangePasswordRequest request) {

        UserLookupService.UserResult userResult = userLookupService.findByEmail(email);

        if (userResult == null) {
            throw new DepartmentNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), userResult.getPassword())) {
            throw new InvalidPasswordException("Old password is incorrect");
        }

        if (userResult.userType() == UserLookupService.UserType.STUDENT) {
            Student student = userResult.getStudent();
            student.setPassword(passwordEncoder.encode(request.getNewPassword()));
            studentRepository.save(student);
            refreshTokenRepository.deleteByStudent(student);
        } else {
            Department department = userResult.getDepartment();
            department.setPassword(passwordEncoder.encode(request.getNewPassword()));
            departmentRepository.save(department);
            refreshTokenRepository.deleteByDepartment(department);
        }
    }



    public void logout(RefreshTokenRequest request) {

        refreshTokenRepository.findByToken(request.getRefreshToken())
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });
    }

}