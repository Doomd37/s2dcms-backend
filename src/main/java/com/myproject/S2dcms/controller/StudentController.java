package com.myproject.S2dcms.controller;

import com.myproject.S2dcms.Service.StudentService;
import com.myproject.S2dcms.dto.auth.ChangePasswordRequest;
import com.myproject.S2dcms.dto.message.MessagePreviewDto;
import com.myproject.S2dcms.dto.message.MessageResponse;
import com.myproject.S2dcms.dto.message.SendMessageRequest;
import com.myproject.S2dcms.dto.student.*;
import com.myproject.S2dcms.dto.verification.ForgotPasswordRequest;
import com.myproject.S2dcms.dto.verification.ResendEmailV;
import com.myproject.S2dcms.dto.verification.ResetPasswordRequest;
import com.myproject.S2dcms.model.Message;
import com.myproject.S2dcms.model.Student;
import com.myproject.S2dcms.repository.StudentRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

        // Registration & Email Verification

        @PostMapping("/auth/register")
        public ResponseEntity<String> register(@RequestBody StudentRegisterRequest request) {
            studentService.register(request);
            return ResponseEntity.ok("Registration successful. Check your email for verification link.");
        }

        @GetMapping("/auth/verify")
        public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
            studentService.verifyEmail(token);
            return ResponseEntity.ok("Email verified successfully.");
        }

        @PostMapping("/auth/resend-verification")
        public ResponseEntity<String> resendVerification(@RequestBody ResendEmailV request) {
            studentService.resendVerification(request);
            return ResponseEntity.ok("Verification email resent.");
        }

        // Profile Management

        @GetMapping("/profile")
        public ResponseEntity<StudentResponse> getProfile() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

            StudentResponse response = studentService.getProfile(email);
            return ResponseEntity.ok(response);
        }

    @PutMapping("/profile")
    public ResponseEntity<StudentResponse> updateProfile(
            @ModelAttribute StudentUpdateDto dto,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(studentService.updateProfile(email, dto, image));
    }

        // Complaints / Messages

        @PostMapping("/complaints")
        public ResponseEntity<MessageResponse> sendComplaint(@ModelAttribute SendMessageRequest request,
                                                             @RequestParam(value = "attachment", required = false) MultipartFile
                                                                     attachment, Principal principal) {
            MessageResponse response = studentService.sendComplaint(principal.getName(), request, attachment);
            return ResponseEntity.ok(response);
        }

    @GetMapping("/complaints")
    public ResponseEntity<Page<MessagePreviewDto>> getComplaints(
            @RequestParam(defaultValue = "ALL") String status,
            @RequestParam(defaultValue = "NEWEST") String sort,
            Pageable pageable) {

        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        Page<MessagePreviewDto> page = studentService.getMyComplaints(
                email, status, sort,
                pageable
        );

        return ResponseEntity.ok(page);
    }

        @GetMapping("/complaints/{id}")
        public ResponseEntity<MessageResponse> openMessage(@PathVariable Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

            MessageResponse response = studentService.openMessage(id,email);

            return ResponseEntity.ok(response);
        }
    }
