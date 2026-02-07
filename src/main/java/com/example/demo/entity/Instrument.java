package com.example.demo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

@Entity
public class Instrument {
    @Id
    @Column(name = "uid", length = 36)
    public String uid; //Идентификатор инструмента
    public String assetUid; // Идентификатор актива
    public int lot; // Размер лота
    public boolean sellAvailableFlag; // Флаг доступности для продажи
    public boolean buyAvailableFlag; // Флаг доступности для покупки
    public boolean weekendFlag; // Флаг торговли в выходные дни
    public String classCode; // Код класса инструмента
    public String ticker; // Биржевой тикер
    public boolean forQualInvestorFlag; // Флаг для квалифицированных инвесторов
    public String positionUid; // Уникальный идентификатор позиции
    public String name; // Название инструмента
    public String exchange; // Биржа
    public String countryOfRisk; // Страна риска
    public String countryOfRiskName; // Название страны риска
    public String isin; // ISIN (International Securities Identification Number)

    //Номинал
    private String nominalCurrency; // валюта
    //Сумма = units + (nano / 1_000_000_000):
    private int nominalUnits; // целая часть
    private int nominalNano; //представления дробной части (копеек * 1_000_000_000)
    private Date updateDate;

    private int lastPriceUnits; // целая часть
    private int lastPriceNano; //представления дробной части (копеек * 1_000_000_000)

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand; // Бренд/логотип

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "bond_uid",
            referencedColumnName = "uid",
            unique = true
    )
    private Bond bond;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "share_uid",
            referencedColumnName = "uid",
            unique = true
    )
    private Share share;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "currency_uid",
            referencedColumnName = "uid",
            unique = true
    )
    private Currency currency;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateDateLocalString(){
        SimpleDateFormat sdfWithZone = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        sdfWithZone.setTimeZone(TimeZone.getDefault());
        return sdfWithZone.format(this.updateDate);
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAssetUid(String assetUid) {
        this.assetUid = assetUid;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }

    public void setSellAvailableFlag(boolean sellAvailableFlag) {
        this.sellAvailableFlag = sellAvailableFlag;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setBuyAvailableFlag(boolean buyAvailableFlag) {
        this.buyAvailableFlag = buyAvailableFlag;
    }

    public void setWeekendFlag(boolean weekendFlag) {
        this.weekendFlag = weekendFlag;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setForQualInvestorFlag(boolean forQualInvestorFlag) {
        this.forQualInvestorFlag = forQualInvestorFlag;
    }

    public void setPositionUid(String positionUid) {
        this.positionUid = positionUid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setCountryOfRisk(String countryOfRisk) {
        this.countryOfRisk = countryOfRisk;
    }

    public void setCountryOfRiskName(String countryOfRiskName) {
        this.countryOfRiskName = countryOfRiskName;
    }

    public String getNominalCurrency() {
        return nominalCurrency;
    }

    public void setNominalCurrency(String nominalCurrency) {
        this.nominalCurrency = nominalCurrency;
    }

    public void setNominalUnits(int nominalUnits) {
        this.nominalUnits = nominalUnits;
    }

    public void setNominalNano(int nominalNano) {
        this.nominalNano = nominalNano;
    }

    public void setBond(Bond bond) {
        this.bond = bond;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getNominalUnits() {
        return nominalUnits;
    }

    public int getNominalNano() {
        return nominalNano;
    }

    public Bond getBond() {
        return bond;
    }

    public Share getShare() {
        return share;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getUid() {
        return uid;
    }

    public String getAssetUid() {
        return assetUid;
    }

    public int getLot() {
        return lot;
    }


    public boolean isSellAvailableFlag() {
        return sellAvailableFlag;
    }


    public Brand getBrand() {
        return brand;
    }

    public boolean isBuyAvailableFlag() {
        return buyAvailableFlag;
    }

    public boolean isWeekendFlag() {
        return weekendFlag;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getTicker() {
        return ticker;
    }

    public boolean isForQualInvestorFlag() {
        return forQualInvestorFlag;
    }

    public int getLastPriceUnits() {
        return lastPriceUnits;
    }

    public int getLastPriceNano() {
        return lastPriceNano;
    }

    public void setLastPriceUnits(int lastPriceUnits) {
        this.lastPriceUnits = lastPriceUnits;
    }

    public void setLastPriceNano(int lastPriceNano) {
        this.lastPriceNano = lastPriceNano;
    }

    public String getPositionUid() {
        return positionUid;
    }

    public String getName() {
        return name;
    }

    public String getExchange() {
        return exchange;
    }

    public String getCountryOfRisk() {
        return countryOfRisk;
    }

    public String getCountryOfRiskName() {
        return countryOfRiskName;
    }

    public String getIsin() {
        return isin;
    }

    @Transient
    private String priceString;

    @Transient
    private String updateDateString;

    public String getUpdateDateString() {
        return updateDateString;
    }

    public void setUpdateDateString(String updateDateString) {
        this.updateDateString = updateDateString;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    @PostLoad
    private void formString() {
        if (nominalCurrency == null || nominalCurrency.isEmpty()) {
            priceString = "";
        }

        int units = lastPriceUnits;
        int nano = lastPriceNano;
        if ( this.bond != null ) {
            units *= 10;
            nano *= 10;
        }

        BigDecimal total = new BigDecimal(units).add(
                new BigDecimal(nano).divide(new BigDecimal("1000000000")))
                        .setScale(3, RoundingMode.HALF_UP);

        priceString = total + " " + nominalCurrency.toUpperCase();
        updateDateString = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(updateDate);
    }


}
