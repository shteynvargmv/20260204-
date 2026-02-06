package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoneyValueDto {
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("units")
    private int units;

    @JsonProperty("nano")
    private int nano;

    public int getNano() {
        return nano;
    }

    public void setNano(int nano) {
        this.nano = nano;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}