package com.myproject.S2dcms.dto.message;

import com.myproject.S2dcms.model.Message;
import com.myproject.S2dcms.model.Message.Status;
import java.time.LocalDateTime;

    public class MessageResponse {

        private Long id;
        private String profilePicturePath;
        private String title;
        private String content;
        private String reply;
        private String status;
        private String attachmentPath;
        private String replyAttachmentPath;
        private LocalDateTime sentAt;
        private LocalDateTime repliedAt;
        private String departmentProfile;

        // Student info for department view
        private String studentName;
        private String studentRegNumber;

        // Department info for student view
        private String departmentName;

        // Constructors
        public MessageResponse() {
        }

        public MessageResponse(Message message) {
            this.id = message.getId();
            this.profilePicturePath=message.getStudent().getProfileImageUrl();
            this.title = message.getTitle();
            this.content = message.getContent();
            this.departmentProfile=message.getDepartment().getDepartmentProfile();
            this.reply = message.getReply();
            this.status = message.getStatus().name(); // enum to string
            this.attachmentPath = message.getAttachmentPath();
            this.replyAttachmentPath = message.getReplyAttachmentPath();
            this.sentAt = message.getSentAt();
            this.repliedAt = message.getRepliedAt();

            if (message.getStudent() != null) {
                this.studentName = message.getStudent().getName();
                this.studentRegNumber = message.getStudent().getRegNo();
            }

            if (message.getDepartment() != null) {
                this.departmentName = message.getDepartment().getDepartmentName();
            }
        }

        // Getters & Setters omitted for brevity (same pattern)

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String getDepartmentProfile() {
            return departmentProfile;
        }

        public void setDepartmentProfile(String departmentProfile) {
            this.departmentProfile = departmentProfile;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAttachmentPath() {
            return attachmentPath;
        }

        public void setAttachmentPath(String attachmentPath) {
            this.attachmentPath = attachmentPath;
        }

        public String getReplyAttachmentPath() {
            return replyAttachmentPath;
        }

        public void setReplyAttachmentPath(String replyAttachmentPath) {
            this.replyAttachmentPath = replyAttachmentPath;
        }

        public LocalDateTime getSentAt() {
            return sentAt;
        }

        public void setSentAt(LocalDateTime sentAt) {
            this.sentAt = sentAt;
        }

        public LocalDateTime getRepliedAt() {
            return repliedAt;
        }

        public void setRepliedAt(LocalDateTime repliedAt) {
            this.repliedAt = repliedAt;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
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
