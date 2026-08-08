package com.myproject.S2dcms.dto.message;

import com.myproject.S2dcms.model.Message;

import java.time.LocalDateTime;

public class MessagePreviewDto {
    private Long id;
    private String profilePicturePath;
    private String snippet;
    private String status;
    private LocalDateTime sentAt;
    private boolean seenByDepartment;
    private boolean seenByStudent;

    // Student info for department view
    private String studentName;
    private String studentEmail;
    private String studentRegNumber;
    private String departmentName;

    public MessagePreviewDto(Message message) {
        this.id = message.getId();
        this.profilePicturePath=message.getStudent().getProfileImageUrl();
        this.snippet = message.getContent().length() > 40
                ? message.getContent().substring(0, 40) + "..."
                : message.getContent();
        this.status = message.getStatus().name(); // convert enum -> string
        this.sentAt = message.getSentAt();
        this.seenByDepartment = message.isSeenByDepartment();
        this.seenByStudent = message.isSeenByStudent();

        if (message.getStudent() != null) {
            this.studentName = message.getStudent().getName();
            this.studentEmail = message.getStudent().getEmail();
            this.studentRegNumber = message.getStudent().getRegNo();
        }

        if (message.getDepartment() != null) {
            this.departmentName = message.getDepartment().getDepartmentName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isSeenByDepartment() {
        return seenByDepartment;
    }

    public void setSeenByDepartment(boolean seenByDepartment) {
        this.seenByDepartment = seenByDepartment;
    }

    public boolean isSeenByStudent() {
        return seenByStudent;
    }

    public void setSeenByStudent(boolean seenByStudent) {
        this.seenByStudent = seenByStudent;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentRegNumber() {
        return studentRegNumber;
    }

    public void setStudentRegNumber(String studentRegNumber) {
        this.studentRegNumber = studentRegNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
