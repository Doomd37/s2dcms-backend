package com.myproject.S2dcms.dto.ai;

public class SuggestReplyRequest {
    private String complaintText;

    public SuggestReplyRequest() {}

    public SuggestReplyRequest(String complaintText) {
        this.complaintText = complaintText;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }
}
