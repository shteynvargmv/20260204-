package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class LastPriceDto {

    @JsonProperty("classCode")
    private String classCode;

    @JsonProperty("ticker")
    private String ticker;

    @JsonProperty("price")
    private QuotationDto price;

    @JsonProperty("instrumentUid")
    private String instrumentUid;

    @JsonProperty("figi")
    private String figi;

    @JsonProperty("time")
    private Date time;

    // Конструкторы
    public LastPriceDto() {
    }

    public LastPriceDto(String classCode, String ticker, QuotationDto price,
                        String instrumentUid, String figi, Date time) {
        this.classCode = classCode;
        this.ticker = ticker;
        this.price = price;
        this.instrumentUid = instrumentUid;
        this.figi = figi;
        this.time = time;
    }

    // Геттеры и сеттеры
    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public QuotationDto getPrice() {
        return price;
    }

    public void setPrice(QuotationDto price) {
        this.price = price;
    }

    public String getInstrumentUid() {
        return instrumentUid;
    }

    public void setInstrumentUid(String instrumentUid) {
        this.instrumentUid = instrumentUid;
    }

    public String getFigi() {
        return figi;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LastPriceDto{" +
                "classCode='" + classCode + '\'' +
                ", ticker='" + ticker + '\'' +
                ", price=" + price +
                ", instrumentUid='" + instrumentUid + '\'' +
                ", figi='" + figi + '\'' +
                ", time=" + time +
                '}';
    }
}