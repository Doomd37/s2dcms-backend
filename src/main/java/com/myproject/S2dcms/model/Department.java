package com.myproject.S2dcms.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // -------------------- Basic Info --------------------
    @Column(name = "department_name", nullable = false, unique = true)
    private String departmentName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "password_reset_token", unique = true)
    private String passwordResetToken;

    @Column(name = "password_reset_token_expiry")
    private LocalDateTime passwordResetTokenExpiry;

    @Column(name = "department_profile")
    private String departmentProfile;

    // -------------------- Role for JWT --------------------
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.DEPARTMENT; // fixed role for JWT

    // -------------------- Relations --------------------
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Message> messages;

    // -------------------- Constructors --------------------
    public Department() {}

    public Department(String departmentName, String email, String password) {
        this.departmentName = departmentName;
        this.email = email.toLowerCase();
        this.password = password;
    }

    // -------------------- Getters & Setters --------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public LocalDateTime getPasswordResetTokenExpiry() {
        return passwordResetTokenExpiry;
    }

    public void setPasswordResetTokenExpiry(LocalDateTime passwordResetTokenExpiry) {
        this.passwordResetTokenExpiry = passwordResetTokenExpiry;
    }
}
