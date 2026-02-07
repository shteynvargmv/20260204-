package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InstrumentsRequest {

    @JsonProperty("instrumentStatus")
    private InstrumentStatus instrumentStatus;

    @JsonProperty("instrumentExchange")
    private InstrumentExchange instrumentExchange;

    @JsonCreator
    public InstrumentsRequest(
            @JsonProperty("instrumentStatus") InstrumentStatus instrumentStatus,
            @JsonProperty("instrumentExchange") InstrumentExchange instrumentExchange) {
        this.instrumentStatus = instrumentStatus;
        this.instrumentExchange = instrumentExchange;
    }

    public InstrumentsRequest() {
        this.instrumentStatus = InstrumentStatus.INSTRUMENT_STATUS_BASE;
        this.instrumentExchange = InstrumentExchange.INSTRUMENT_EXCHANGE_UNSPECIFIED;
    }

    public InstrumentStatus getInstrumentStatus() {
        return instrumentStatus;
    }

    public void setInstrumentStatus(InstrumentStatus instrumentStatus) {
        this.instrumentStatus = instrumentStatus;
    }

    public InstrumentExchange getInstrumentExchange() {
        return instrumentExchange;
    }

    public void setInstrumentExchange(InstrumentExchange instrumentExchange) {
        this.instrumentExchange = instrumentExchange;
    }

    @Override
    public String toString() {
        return "InstrumentInfo{" +
                "instrumentStatus=" + instrumentStatus +
                ", instrumentExchange=" + instrumentExchange +
                '}';
    }
}