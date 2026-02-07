package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuotationDto {
    @JsonProperty("nano")
    private int nano;

    @JsonProperty("units")
    private String units;

    public int getNano() {
        return nano;
    }

    public void setNano(int nano) {
        this.nano = nano;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }


}
