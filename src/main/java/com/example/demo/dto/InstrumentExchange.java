package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum InstrumentExchange {

    // Значение из JSON
    INSTRUMENT_EXCHANGE_UNSPECIFIED("INSTRUMENT_EXCHANGE_UNSPECIFIED"),

    // Возможные дополнительные значения (добавьте по необходимости)
    INSTRUMENT_EXCHANGE_MOEX("INSTRUMENT_EXCHANGE_MOEX"),
    INSTRUMENT_EXCHANGE_RTS("INSTRUMENT_EXCHANGE_RTS"),
    INSTRUMENT_EXCHANGE_SPB("INSTRUMENT_EXCHANGE_SPB"),
    INSTRUMENT_EXCHANGE_OTC("INSTRUMENT_EXCHANGE_OTC");

    private final String value;

    InstrumentExchange(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static InstrumentExchange fromValue(String value) {
        for (InstrumentExchange exchange : InstrumentExchange.values()) {
            if (exchange.value.equals(value)) {
                return exchange;
            }
        }
        // Возвращаем UNSPECIFIED по умолчанию или кидаем исключение
        return INSTRUMENT_EXCHANGE_UNSPECIFIED;
        // Или, если нужно строгое соответствие:
        // throw new IllegalArgumentException("Unknown InstrumentExchange: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}