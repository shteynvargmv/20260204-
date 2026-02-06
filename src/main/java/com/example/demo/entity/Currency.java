package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

//Валюта
@Entity
public class Currency {
    @Id
    @Column(name = "uid", length = 36)
    public String uid; //Идентификатор инструмента
    public String isoCurrencyName; // Название валюты по стандарту ISO

    @OneToOne(mappedBy = "currency")
    private Instrument instrument;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setIsoCurrencyName(String isoCurrencyName) {
        this.isoCurrencyName = isoCurrencyName;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public String getUid() {
        return uid;
    }

    public String getIsoCurrencyName() {
        return isoCurrencyName;
    }

    public Instrument getInstrument() {
        return instrument;
    }
}
