package com.example.demo.model;

public class CurrencyData {
    private String symbol;
    private String name;
    private String symbol_native;
    private int decimal_digits;
    private int rounding;
    private String code;
    private String name_plural;

    public CurrencyData() {
    }

    public CurrencyData(String symbol, String name, String symbol_native, int decimal_digits, int rounding, String code, String name_plural) {
        this.symbol = symbol;
        this.name = name;
        this.symbol_native = symbol_native;
        this.decimal_digits = decimal_digits;
        this.rounding = rounding;
        this.code = code;
        this.name_plural = name_plural;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol_native() {
        return symbol_native;
    }

    public int getDecimal_digits() {
        return decimal_digits;
    }

    public int getRounding() {
        return rounding;
    }

    public String getCode() {
        return code;
    }

    public String getName_plural() {
        return name_plural;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol_native(String symbol_native) {
        this.symbol_native = symbol_native;
    }

    public void setDecimal_digits(int decimal_digits) {
        this.decimal_digits = decimal_digits;
    }

    public void setRounding(int rounding) {
        this.rounding = rounding;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName_plural(String name_plural) {
        this.name_plural = name_plural;
    }

    @Override
    public String toString() {
        return "CurrencyData{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", symbol_native='" + symbol_native + '\'' +
                ", decimal_digits=" + decimal_digits +
                ", rounding=" + rounding +
                ", code='" + code + '\'' +
                ", name_plural='" + name_plural + '\'' +
                '}';
    }
}
