package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrandDto {
    @JsonProperty("logoName")
    private String logoName;

    @JsonProperty("logoBaseColor")
    private String logoBaseColor;

    @JsonProperty("textColor")
    private String textColor;

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getLogoBaseColor() {
        return logoBaseColor;
    }

    public void setLogoBaseColor(String logoBaseColor) {
        this.logoBaseColor = logoBaseColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
