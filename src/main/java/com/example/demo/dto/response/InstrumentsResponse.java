package com.example.demo.dto.response;


import com.example.demo.dto.InstrumentDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InstrumentsResponse {
    @JsonProperty("instruments")
    private List<InstrumentDto> instruments;

    public List<InstrumentDto> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<InstrumentDto> instruments) {
        this.instruments = instruments;
    }
}