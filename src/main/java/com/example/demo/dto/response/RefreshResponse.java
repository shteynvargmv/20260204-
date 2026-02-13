package com.example.demo.dto.response;

import com.example.demo.dto.InstrumentDto;

public class RefreshResponse {
    private InstrumentDto instrumentDto;
    private String updateDateLocalString;
    private String priceString;
    private String refreshResult;

    public RefreshResponse(InstrumentDto instrumentDto, String updateDateLocalString, String priceString) {
        this.instrumentDto = instrumentDto;
        this.updateDateLocalString = updateDateLocalString;
        this.priceString = priceString;
    }

    public RefreshResponse(String refreshResult) {
        this.refreshResult = refreshResult;
    }

    public InstrumentDto getInstrumentDto() {
        return instrumentDto;
    }

    public String getRefreshResult() {
        return refreshResult;
    }

    public void setInstrumentDto(InstrumentDto instrumentDto) {
        this.instrumentDto = instrumentDto;
    }

    public void setRefreshResult(String refreshResult) {
        this.refreshResult = refreshResult;
    }

    public String getUpdateDateLocalString() {
        return updateDateLocalString;
    }

    public void setUpdateDateLocalString(String updateDateLocalString) {
        this.updateDateLocalString = updateDateLocalString;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }
}
