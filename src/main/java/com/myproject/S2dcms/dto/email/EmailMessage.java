package com.myproject.S2dcms.dto.email;

public class EmailMessage {
    private String to;
    private String subject;
    private String type; // VERIFICATION, PASSWORD_RESET, etc.
    private String token;
    private String name;

    public EmailMessage() {}

    public EmailMessage(String to, String subject, String type, String token, String name) {
        this.to = to;
        this.subject = subject;
        this.type = type;
        this.token = token;
        this.name = name;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
