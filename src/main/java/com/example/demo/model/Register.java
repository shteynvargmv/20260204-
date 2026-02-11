package com.example.demo.model;

public class Register {
    private boolean created;
    private String registerResult;

    public Register(boolean created) {
        this.created = created;
    }

    public Register(String registerResult) {
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
