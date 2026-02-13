package com.example.demo.dto.request;

import com.example.demo.dto.InstrumentStatus;
import com.example.demo.dto.InstrumentType;


public class AssetsRequest {
    private InstrumentType instrumentType;
    private InstrumentStatus instrumentStatus;

    public AssetsRequest() {
    }

    public AssetsRequest(InstrumentType instrumentType) {
        this.instrumentType = instrumentType;
        this.instrumentStatus = InstrumentStatus.INSTRUMENT_STATUS_BASE;
    }

    public InstrumentType getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(InstrumentType instrumentType) {
        this.instrumentType = instrumentType;
    }

    public InstrumentStatus getInstrumentStatus() {
        return instrumentStatus;
    }

    public void setInstrumentStatus(InstrumentStatus instrumentStatus) {
        this.instrumentStatus = instrumentStatus;
    }

}
