package com.myproject.S2dcms.dto.department;

import com.myproject.S2dcms.model.Department;

public class DepartmentResponse {
    private Long id;
    private String departmentProfile;
    private String departmentName;
    private String email;

    public DepartmentResponse() {
    }

    public DepartmentResponse(Department department) {
        this.departmentProfile=department.getDepartmentProfile();
        this.departmentName = department.getDepartmentName();
        this.email = department.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentProfile() {
        return departmentProfile;
    }

    public void setDepartmentProfile(String departmentProfile) {
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
}
