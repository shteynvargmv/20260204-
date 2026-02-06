package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.Date;

//Облигация
@Entity
public class Bond {
    @Id
    @Column(name = "uid", length = 36)
    public String uid; //Идентификатор инструмента

    public Date callDate; // Дата досрочного выкупа (оферты)

    public Date maturityDate; // Дата погашения

    // Накопленный купонный доход (НКД)
    private String aciСurrency; // валюта
    //Сумма = units + (nano / 1_000_000_000):
    private int aciUnits; // целая часть
    private int aciNano; //представления дробной части (копеек * 1_000_000_000)

    public int couponQuantityPerYear; // Количество купонов в год

    public boolean floatingCouponFlag; // Флаг плавающего купона

    public boolean subordinatedFlag; // Флаг субординированности (субординированные облигации)

    public boolean amortizationFlag; // Флаг амортизации (амортизируемые облигации)

    public boolean perpetualFlag; // Флаг бессрочности (вечные облигации)

    @OneToOne(mappedBy = "bond")
    private Instrument instrument;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public void setAciСurrency(String aciСurrency) {
        this.aciСurrency = aciСurrency;
    }

    public void setAciUnits(int aciUnits) {
        this.aciUnits = aciUnits;
    }

    public void setAciNano(int aciNano) {
        this.aciNano = aciNano;
    }

    public void setCouponQuantityPerYear(int couponQuantityPerYear) {
        this.couponQuantityPerYear = couponQuantityPerYear;
    }

    public void setFloatingCouponFlag(boolean floatingCouponFlag) {
        this.floatingCouponFlag = floatingCouponFlag;
    }

    public void setSubordinatedFlag(boolean subordinatedFlag) {
        this.subordinatedFlag = subordinatedFlag;
    }

    public void setAmortizationFlag(boolean amortizationFlag) {
        this.amortizationFlag = amortizationFlag;
    }

    public void setPerpetualFlag(boolean perpetualFlag) {
        this.perpetualFlag = perpetualFlag;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public String getUid() {
        return uid;
    }

    public Date getCallDate() {
        return callDate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public String getAciСurrency() {
        return aciСurrency;
    }

    public int getAciUnits() {
        return aciUnits;
    }

    public int getAciNano() {
        return aciNano;
    }

    public int getCouponQuantityPerYear() {
        return couponQuantityPerYear;
    }

    public boolean isFloatingCouponFlag() {
        return floatingCouponFlag;
    }

    public boolean isSubordinatedFlag() {
        return subordinatedFlag;
    }

    public boolean isAmortizationFlag() {
        return amortizationFlag;
    }

    public boolean isPerpetualFlag() {
        return perpetualFlag;
    }

    public Instrument getInstrument() {
        return instrument;
    }
}
