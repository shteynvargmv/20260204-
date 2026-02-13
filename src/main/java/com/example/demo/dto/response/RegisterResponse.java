package com.example.demo.dto.response;

public class RegisterResponse {
    private boolean created;
    private String registerResult;

    public RegisterResponse(boolean created) {
        this.created = created;
    }

    public RegisterResponse(String registerResult) {
        this.registerResult = registerResult;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public void setRegisterResult(String registerResult) {
        this.registerResult = registerResult;
    }

    public boolean isCreated() {
        return created;
    }

    public String getRegisterResult() {
        return registerResult;
    }
}
