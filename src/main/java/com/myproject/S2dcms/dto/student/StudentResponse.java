package com.myproject.S2dcms.dto.student;

public class StudentResponse {

    private String name;
    private String regNo;
    private String email;
    private String departmentName;
    private String profilePicturePath; // optional for logged-in user
    private boolean emailVerified;

    // Constructors
    public StudentResponse() {}

    public StudentResponse(com.myproject.S2dcms.model.Student student) {
        this.name = student.getName();
        this.regNo = student.getRegNo();
        this.email = student.getEmail();
        this.departmentName = student.getDepartment().getDepartmentName();
        this.profilePicturePath = student.getProfileImageUrl();
        this.emailVerified = student.isEmailVerified();
    }

    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
