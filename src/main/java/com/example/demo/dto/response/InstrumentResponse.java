package com.example.demo.dto.response;


import com.example.demo.dto.InstrumentDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InstrumentResponse {
    @JsonProperty("instrument")
    private InstrumentDto instrument;

    public InstrumentDto getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentDto instrument) {
        this.instrument = instrument;
    }

    @Override
    public String toString() {
        return "InstrumentResponse{" +
                "instrument=" + instrument +
                '}';
    }
}