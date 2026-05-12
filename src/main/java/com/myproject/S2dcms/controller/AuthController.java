package com.myproject.S2dcms.controller;

import com.myproject.S2dcms.Service.AuthService;
import com.myproject.S2dcms.dto.auth.AuthResponse;
import com.myproject.S2dcms.dto.auth.ChangePasswordRequest;
import com.myproject.S2dcms.dto.auth.LoginRequest;
import com.myproject.S2dcms.dto.auth.RefreshTokenRequest;
import com.myproject.S2dcms.dto.verification.ForgotPasswordRequest;
import com.myproject.S2dcms.dto.verification.ResetPasswordRequest;
import com.myproject.S2dcms.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestBody RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/auth/forgot-password")
    public ResponseEntity<Void> forgotPassword(
            @RequestBody ForgotPasswordRequest request
    ) {
        authService.forgotPassword(request);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/auth/reset-password")
    public ResponseEntity<Void> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        authService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequest request
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        authService.changePassword(email, request);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(
            @RequestBody RefreshTokenRequest request
    ) {
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }
}