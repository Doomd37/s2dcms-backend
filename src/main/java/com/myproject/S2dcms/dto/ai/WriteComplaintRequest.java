package com.myproject.S2dcms.dto.ai;

public class WriteComplaintRequest {
    private String situation;

    public WriteComplaintRequest() {}

    public WriteComplaintRequest(String situation) {
        this.situation = situation;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }
}
