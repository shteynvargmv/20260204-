package com.example.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Entity
@DynamicUpdate
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

    private BigDecimal price;

    private String brCodeName;
    @Column(length = 1000) // Или
    private String description;
    private String nameLong;

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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "currency_symbol",
                referencedColumnName = "id")
    private CurrencySymbol symbol;

    @OneToMany(mappedBy = "instrument", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    @Transient
    private String updateDateString;

    @Transient
    private String typeString;

    @Transient
    private String priceString;

    public CurrencySymbol getSymbol() {
        return symbol;
    }

    public void setSymbol(CurrencySymbol symbol) {
        this.symbol = symbol;
    }

    public String getTypeString() {
        return typeString;
    }

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

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public String getUpdateDateString() {
        return updateDateString;
    }

    public void setUpdateDateString(String updateDateString) {
        this.updateDateString = updateDateString;
    }

    public String getBrCodeName() {
        return brCodeName;
    }

    public void setBrCodeName(String brCodeName) {
        this.brCodeName = brCodeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setNameLong(String nameLong) {
        this.nameLong = nameLong;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public void setFavorites(Favorite favorite) {
        this.favorites = favorites != null
                ? List.copyOf(favorites)
                : List.of();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public String getPriceString() {
        String curr;
        CurrencySymbol symbol = this.getSymbol();
        if (symbol != null){
            curr = this.getSymbol().getSymb();
        } else {
            curr = this.getNominalCurrency().toUpperCase();
        }
        return this.price + " " + curr;
    }

    @PostLoad
    private void formString() {

        updateDateString = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(updateDate);

        if (share != null){
            typeString = "Акция";
        }
        if (bond != null){
            typeString = "Облигация";
        }
        if (currency != null){
            typeString = "Валюта";
        }
    }

}
