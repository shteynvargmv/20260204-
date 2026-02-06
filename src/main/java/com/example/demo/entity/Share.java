package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.Date;

//Акция
@Entity
public class Share {
    @Id
    @Column(name = "uid", length = 36)
    public String uid; //Идентификатор инструмента

    public boolean divYieldFlag; // Флаг дивидендной доходности

    public Date ipoDate; // Дата IPO (Initial Public Offering)

    public String sector; // Сектор экономики

    public boolean liquidityFlag; // Флаг ликвидности

    @OneToOne(mappedBy = "share")
    private Instrument instrument;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDivYieldFlag(boolean divYieldFlag) {
        this.divYieldFlag = divYieldFlag;
    }

    public void setIpoDate(Date ipoDate) {
        this.ipoDate = ipoDate;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setLiquidityFlag(boolean liquidityFlag) {
        this.liquidityFlag = liquidityFlag;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public String getUid() {
        return uid;
    }

    public boolean isDivYieldFlag() {
        return divYieldFlag;
    }

    public Date getIpoDate() {
        return ipoDate;
    }

    public String getSector() {
        return sector;
    }

    public boolean isLiquidityFlag() {
        return liquidityFlag;
    }

    public Instrument getInstrument() {
        return instrument;
    }
}
