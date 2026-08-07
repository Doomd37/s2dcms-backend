package com.myproject.S2dcms.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateDepartmentRequest {
    
    @NotBlank(message = "Department name is required")
    @Size(min = 2, max = 100, message = "Department name must be between 2 and 100 characters")
    private String departmentName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    private String departmentProfile;

    public CreateDepartmentRequest() {}

    public CreateDepartmentRequest(String departmentName, String email, String password, String departmentProfile) {
        this.departmentName = departmentName;
        this.email = email;
        this.password = password;
        this.departmentProfile = departmentProfile;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartmentProfile() {
        return departmentProfile;
    }

    public void setDepartmentProfile(String departmentProfile) {
        this.departmentProfile = departmentProfile;
    }
}
