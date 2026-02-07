package com.example.demo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class InstrumentResponse {
    @JsonProperty("instruments")
    private InstrumentDto instrument;

    public InstrumentDto getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentDto instrument) {
        this.instrument = instrument;
    }
}