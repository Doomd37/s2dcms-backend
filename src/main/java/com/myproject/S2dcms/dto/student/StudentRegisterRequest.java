package com.myproject.S2dcms.dto.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentRegisterRequest {

        @NotBlank
        private String name;

        @NotBlank
        private String regNo;

        @Email
        @NotBlank
        private String email;

        @Size(min = 6)
        private String password;

        private Long departmentId; // department dropdown selected by student

        // Getters & Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getRegNo() { return regNo; }
        public void setRegNo(String regNo) { this.regNo = regNo; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public Long getDepartmentId() { return departmentId; }
        public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    }
