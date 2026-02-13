package com.example.demo.dto.request;

public class AssetRequest {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AssetRequest(String id) {
        this.id = id;
    }
}
