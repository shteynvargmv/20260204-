package com.example.demo.model;

import com.example.demo.entity.Instrument;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class CurrencySymbol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String symb;
    private String name;
    private String symbolNative;
    private int decimalDigits;
    private int rounding;
    private String code;
    private String namePlural;

    @OneToMany(mappedBy = "symbol")
    private Set<Instrument> instruments = new HashSet<>();

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymb() {
        return symb;
    }

    public void setSymb(String symb) {
        this.symb = symb;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDecimalDigits(int decimal_digits) {
        this.decimalDigits = decimal_digits;
    }

    public void setSymbolNative(String symbol_native) {
        this.symbolNative = symbol_native;
    }

    public void setRounding(int rounding) {
        this.rounding = rounding;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNamePlural(String name_plural) {
        this.namePlural = name_plural;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbolNative() {
        return symbolNative;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public int getRounding() {
        return rounding;
    }

    public String getCode() {
        return code;
    }

    public String getNamePlural() {
        return namePlural;
    }

    public Set<Instrument> getInstruments() {
        return instruments;
    }
}
