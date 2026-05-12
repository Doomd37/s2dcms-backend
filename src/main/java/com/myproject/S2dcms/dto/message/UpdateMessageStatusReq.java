package com.myproject.S2dcms.dto.message;


import com.myproject.S2dcms.model.Message.Status;
import jakarta.validation.constraints.NotNull;

public class UpdateMessageStatusReq {

    @NotNull
    private Long messageId;

    @NotNull
    private Status status;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
