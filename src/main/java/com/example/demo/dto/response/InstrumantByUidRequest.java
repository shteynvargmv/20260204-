package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstrumantByUidRequest {

    @JsonProperty("idType")
    private IdType idType;

    @JsonProperty("classCode")
    private String classCode;

    @JsonProperty("id")
    private String id;


    public InstrumantByUidRequest() {
        this.idType = IdType.INSTRUMENT_ID_TYPE_UID;
        this.classCode = "";
    }

    public InstrumantByUidRequest(IdType idType, String classCode, String id) {
        this.idType = idType;
        this.classCode = classCode;
        this.id = id;
    }


    public enum IdType {
        @JsonProperty("INSTRUMENT_ID_UNSPECIFIED")
        INSTRUMENT_ID_UNSPECIFIED,

        @JsonProperty("INSTRUMENT_ID_TYPE_FIGI")
        INSTRUMENT_ID_TYPE_FIGI,

        @JsonProperty("INSTRUMENT_ID_TYPE_TICKER")
        INSTRUMENT_ID_TYPE_TICKER,

        @JsonProperty("INSTRUMENT_ID_TYPE_UID")
        INSTRUMENT_ID_TYPE_UID,

        @JsonProperty("INSTRUMENT_ID_TYPE_POSITION_UID")
        INSTRUMENT_ID_TYPE_POSITION_UID,
    }

    public IdType getIdType() {
        return idType;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getId() {
        return id;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "InstrumantByUidRequest{" +
                "idType=" + idType +
                ", classCode='" + classCode + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}