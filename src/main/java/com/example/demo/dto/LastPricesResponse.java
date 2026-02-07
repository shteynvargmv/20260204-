package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.dto.LastPriceDto;

import java.util.List;

public class LastPricesResponse {

    @JsonProperty("lastPrices")
    private List<LastPriceDto> lastPrices;

    // Конструкторы
    public LastPricesResponse() {
    }

    public LastPricesResponse(List<LastPriceDto> lastPrices) {
        this.lastPrices = lastPrices;
    }

    // Геттеры и сеттеры
    public List<LastPriceDto> getLastPrices() {
        return lastPrices;
    }

    public void setLastPrices(List<LastPriceDto> lastPrices) {
        this.lastPrices = lastPrices;
    }

    @Override
    public String toString() {
        return "LastPricesResponse{" +
                "lastPrices=" + lastPrices +
                '}';
    }




}