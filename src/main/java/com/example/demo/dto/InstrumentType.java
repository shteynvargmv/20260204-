package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum InstrumentType {

        INSTRUMENT_TYPE_UNSPECIFIED("INSTRUMENT_TYPE_UNSPECIFIED"),
        INSTRUMENT_TYPE_BOND("INSTRUMENT_TYPE_BOND"),
        INSTRUMENT_TYPE_SHARE("INSTRUMENT_TYPE_SHARE"),
        INSTRUMENT_TYPE_CURRENCY("INSTRUMENT_TYPE_CURRENCY"),
        INSTRUMENT_TYPE_ETF("INSTRUMENT_TYPE_ETF"),
        INSTRUMENT_TYPE_FUTURES("INSTRUMENT_TYPE_FUTURES"),
        INSTRUMENT_TYPE_SP("INSTRUMENT_TYPE_SP"),
        INSTRUMENT_TYPE_OPTION("INSTRUMENT_TYPE_OPTION"),
        INSTRUMENT_TYPE_CLEARING_CERTIFICATE("INSTRUMENT_TYPE_CLEARING_CERTIFICATE"),
        INSTRUMENT_TYPE_INDEX("INSTRUMENT_TYPE_INDEX"),
        INSTRUMENT_TYPE_COMMODITY("INSTRUMENT_TYPE_COMMODITY");

        private final String value;

        InstrumentType(String value) {
            this.value = value;
        }

        @JsonCreator
        public static InstrumentType fromString(String value) {
            if (value == null) return null;
            for (InstrumentType type : InstrumentType.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            try {
                return InstrumentType.valueOf(value);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        @Override
        public String toString() {
            return value;
        }

        public String getValue() {
            return value;
        }
    }