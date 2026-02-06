package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum InstrumentStatus {

    // Значение из JSON
    INSTRUMENT_STATUS_UNSPECIFIED("INSTRUMENT_STATUS_UNSPECIFIED"),

    // Возможные дополнительные значения (добавьте по необходимости)
    INSTRUMENT_STATUS_BASE("INSTRUMENT_STATUS_BASE"),
    INSTRUMENT_STATUS_ALL("INSTRUMENT_STATUS_ALL");

    private final String value;

    InstrumentStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static InstrumentStatus fromValue(String value) {
        for (InstrumentStatus status : InstrumentStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        // Возвращаем UNSPECIFIED по умолчанию или кидаем исключение
        return INSTRUMENT_STATUS_UNSPECIFIED;
        // Или, если нужно строгое соответствие:
        // throw new IllegalArgumentException("Unknown InstrumentStatus: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
