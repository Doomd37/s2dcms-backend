package com.myproject.S2dcms.dto.message;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReplyMessageRequest {

    @NotNull
    private Long messageId;

    @NotBlank
    private String reply;


    // Getters & Setters
    public Long getMessageId() { return messageId; }
    public void setMessageId(Long messageId) { this.messageId = messageId; }

    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }

}

