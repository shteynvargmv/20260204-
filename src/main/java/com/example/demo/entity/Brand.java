package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logoName;

    private String logoBaseColor;

    private String textColor;

    @OneToMany(mappedBy = "brand")
    private Set<Instrument> instruments = new HashSet<>();

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getLogoBaseColor() {
        return logoBaseColor;
    }

    public void setLogoBaseColor(String logoBaseColor) {
        this.logoBaseColor = logoBaseColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getLogo(){
        if (logoName != null && logoName.length() >= 4) {
            return logoName.substring(0, logoName.length() - 4);
        }
        return null;
    }
    public String getLogoExt(){
        if (logoName != null && logoName.length() >= 4) {
            return logoName.substring(logoName.length() - 4);
        }
        return null;
    }
}

