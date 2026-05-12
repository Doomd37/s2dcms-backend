package com.myproject.S2dcms.dto.verification;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ResendEmailV {

        @Email
        @NotBlank
        private String email;

        // Getters & Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }


