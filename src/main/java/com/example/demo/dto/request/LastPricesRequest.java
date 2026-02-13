package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LastPricesRequest {

    @JsonProperty("instrumentId")
    private List<String> instrumentId;

    @JsonProperty("lastPriceType")
    private LastPriceType lastPriceType;

    @JsonProperty("instrumentStatus")
    private InstrumentStatus instrumentStatus;

    public LastPricesRequest() {
            this.lastPriceType = LastPriceType.LAST_PRICE_EXCHANGE;
            this.instrumentStatus = InstrumentStatus.INSTRUMENT_STATUS_ALL;
    }

    public LastPricesRequest(List<String> instrumentId,
                             LastPriceType lastPriceType,
                             InstrumentStatus instrumentStatus) {
        this.instrumentId = instrumentId;
        this.lastPriceType = lastPriceType;
        this.instrumentStatus = instrumentStatus;
    }

    public List<String> getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(List<String> instrumentId) {
        this.instrumentId = instrumentId;
    }

    public LastPriceType getLastPriceType() {
        return lastPriceType;
    }

    public void setLastPriceType(LastPriceType lastPriceType) {
        this.lastPriceType = lastPriceType;
    }

    public InstrumentStatus getInstrumentStatus() {
        return instrumentStatus;
    }

    public void setInstrumentStatus(InstrumentStatus instrumentStatus) {
        this.instrumentStatus = instrumentStatus;
    }

    public enum LastPriceType {
        @JsonProperty("LAST_PRICE_UNSPECIFIED")
        LAST_PRICE_UNSPECIFIED, //Не определен
        @JsonProperty("LAST_PRICE_EXCHANGE")
        LAST_PRICE_EXCHANGE, // Цена биржи.
        @JsonProperty("LAST_PRICE_DEALER")
        LAST_PRICE_DEALER // Цена дилера
    }

    public enum InstrumentStatus {
        @JsonProperty("INSTRUMENT_STATUS_UNSPECIFIED")
        INSTRUMENT_STATUS_UNSPECIFIED,

        @JsonProperty("INSTRUMENT_STATUS_BASE")
        INSTRUMENT_STATUS_BASE,

        @JsonProperty("INSTRUMENT_STATUS_ALL")
        INSTRUMENT_STATUS_ALL,

    }

    @Override
    public String toString() {
        return "LastPricesRequest{" +
                "instrumentId=" + instrumentId +
                ", lastPriceType=" + lastPriceType +
                ", instrumentStatus=" + instrumentStatus +
                '}';
    }
}