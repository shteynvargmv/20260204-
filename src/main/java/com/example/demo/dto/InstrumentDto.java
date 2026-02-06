package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;


public class InstrumentDto {

    // ==================== ОБЩИЕ АТРИБУТЫ (есть во всех типах) ====================
    @JsonProperty("assetUid")
    public String assetUid;

    @JsonProperty("figi")
    public String figi;

    @JsonProperty("dshortMin")
    public QuotationDto dshortMin;

    @JsonProperty("countryOfRisk")
    public String countryOfRisk;

    @JsonProperty("lot")
    public int lot;

    @JsonProperty("uid")
    public String uid;

    @JsonProperty("requiredTests")
    public List<String> requiredTests;

    @JsonProperty("blockedTcaFlag")
    public boolean blockedTcaFlag;

    @JsonProperty("dlong")
    public QuotationDto dlong;

    @JsonProperty("dlongClient")
    public QuotationDto dlongClient;

    @JsonProperty("nominal")
    public MoneyValueDto nominal;

    @JsonProperty("sellAvailableFlag")
    public boolean sellAvailableFlag;

    @JsonProperty("currency")
    public String currency;

    @JsonProperty("first1dayCandleDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date first1dayCandleDate;

    @JsonProperty("brand")
    public BrandDto brand;

    @JsonProperty("buyAvailableFlag")
    public boolean buyAvailableFlag;

    @JsonProperty("weekendFlag")
    public boolean weekendFlag;

    @JsonProperty("classCode")
    public String classCode;

    @JsonProperty("ticker")
    public String ticker;

    @JsonProperty("forQualInvestorFlag")
    public boolean forQualInvestorFlag;

    @JsonProperty("forIisFlag")
    public boolean forIisFlag;

    @JsonProperty("positionUid")
    public String positionUid;

    @JsonProperty("apiTradeAvailableFlag")
    public boolean apiTradeAvailableFlag;

    @JsonProperty("dlongMin")
    public QuotationDto dlongMin;

    @JsonProperty("shortEnabledFlag")
    public boolean shortEnabledFlag;

    @JsonProperty("kshort")
    public QuotationDto kshort;

    @JsonProperty("first1minCandleDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date first1minCandleDate;

    @JsonProperty("minPriceIncrement")
    public QuotationDto minPriceIncrement;

    @JsonProperty("otcFlag")
    public boolean otcFlag;

    @JsonProperty("dshortClient")
    public QuotationDto dshortClient;

    @JsonProperty("klong")
    public QuotationDto klong;

    @JsonProperty("dshort")
    public QuotationDto dshort;

    @JsonProperty("name")
    public String name;

    @JsonProperty("exchange")
    public String exchange;

    @JsonProperty("countryOfRiskName")
    public String countryOfRiskName;

    @JsonProperty("isin")
    public String isin;

    // ==================== АТРИБУТЫ ОБЛИГАЦИЙ (BONDS) ====================
    @JsonProperty("callDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date callDate; // 🔴 ТОЛЬКО Bonds: Дата досрочного выкупа

    @JsonProperty("maturityDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date maturityDate; // 🔴 ТОЛЬКО Bonds: Дата погашения

    @JsonProperty("placementPrice")
    public MoneyValueDto placementPrice; // 🔴 ТОЛЬКО Bonds: Цена размещения

    @JsonProperty("aciValue")
    public MoneyValueDto aciValue; // 🔴 ТОЛЬКО Bonds: Накопленный купонный доход

    @JsonProperty("initialNominal")
    public MoneyValueDto initialNominal; // 🔴 ТОЛЬКО Bonds: Начальный номинал

    @JsonProperty("couponQuantityPerYear")
    public int couponQuantityPerYear; // 🔴 ТОЛЬКО Bonds: Количество купонов в год

    @JsonProperty("floatingCouponFlag")
    public boolean floatingCouponFlag; // 🔴 ТОЛЬКО Bonds: Флаг плавающего купона

    @JsonProperty("subordinatedFlag")
    public boolean subordinatedFlag; // 🔴 ТОЛЬКО Bonds: Флаг субординированности

    @JsonProperty("amortizationFlag")
    public boolean amortizationFlag; // 🔴 ТОЛЬКО Bonds: Флаг амортизации

    @JsonProperty("perpetualFlag")
    public boolean perpetualFlag; // 🔴 ТОЛЬКО Bonds: Флаг бессрочности

    @JsonProperty("issueKind")
    public String issueKind; // 🔴 ТОЛЬКО Bonds: Вид выпуска

    @JsonProperty("placementDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date placementDate; // 🔴 ТОЛЬКО Bonds: Дата размещения

    @JsonProperty("stateRegDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date stateRegDate; // 🔴 ТОЛЬКО Bonds: Дата госрегистрации

    @JsonProperty("issueSizePlan")
    public String issueSizePlan; // 🔴 ТОЛЬКО Bonds, 🔵 ТОЛЬКО Shares: План объема выпуска

    @JsonProperty("issueSize")
    public String issueSize; // 🔴 ТОЛЬКО Bonds, 🔵 ТОЛЬКО Shares: Объем выпуска

    // ==================== АТРИБУТЫ АКЦИЙ (SHARES) ====================
    @JsonProperty("divYieldFlag")
    public boolean divYieldFlag; // 🔵 ТОЛЬКО Shares: Флаг дивидендной доходности

    @JsonProperty("ipoDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    public Date ipoDate; // 🔵 ТОЛЬКО Shares: Дата IPO

    @JsonProperty("sector")
    public String sector; // 🔵 ТОЛЬКО Shares: Сектор экономики

    @JsonProperty("liquidityFlag")
    public boolean liquidityFlag; // 🔵 ТОЛЬКО Shares: Флаг ликвидности

    // ==================== АТРИБУТЫ ВАЛЮТ (CURRENCIES) ====================
    @JsonProperty("isoCurrencyName")
    public String isoCurrencyName; // 🟢 ТОЛЬКО Currencies: Название валюты по ISO

    // Метод для определения типа инструмента
    public String getInstrumentType() {
        if (divYieldFlag || ipoDate != null) {
            return "share"; // Акция
        } else if (callDate != null || maturityDate != null || couponQuantityPerYear > 0) {
            return "bond"; // Облигация
        } else if (isoCurrencyName != null) {
            return "currency"; // Валюта
        } else {
            return "unknown"; // Неизвестно
        }
    }

    public void setAssetUid(String assetUid) {
        this.assetUid = assetUid;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    public void setDshortMin(QuotationDto dshortMin) {
        this.dshortMin = dshortMin;
    }

    public void setCountryOfRisk(String countryOfRisk) {
        this.countryOfRisk = countryOfRisk;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setRequiredTests(List<String> requiredTests) {
        this.requiredTests = requiredTests;
    }

    public void setBlockedTcaFlag(boolean blockedTcaFlag) {
        this.blockedTcaFlag = blockedTcaFlag;
    }

    public void setDlong(QuotationDto dlong) {
        this.dlong = dlong;
    }

    public void setDlongClient(QuotationDto dlongClient) {
        this.dlongClient = dlongClient;
    }

    public void setNominal(MoneyValueDto nominal) {
        this.nominal = nominal;
    }

    public void setSellAvailableFlag(boolean sellAvailableFlag) {
        this.sellAvailableFlag = sellAvailableFlag;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setFirst1dayCandleDate(Date first1dayCandleDate) {
        this.first1dayCandleDate = first1dayCandleDate;
    }

    public void setBrand(BrandDto brand) {
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

    public void setForIisFlag(boolean forIisFlag) {
        this.forIisFlag = forIisFlag;
    }

    public void setPositionUid(String positionUid) {
        this.positionUid = positionUid;
    }

    public void setApiTradeAvailableFlag(boolean apiTradeAvailableFlag) {
        this.apiTradeAvailableFlag = apiTradeAvailableFlag;
    }

    public void setDlongMin(QuotationDto dlongMin) {
        this.dlongMin = dlongMin;
    }

    public void setShortEnabledFlag(boolean shortEnabledFlag) {
        this.shortEnabledFlag = shortEnabledFlag;
    }

    public void setKshort(QuotationDto kshort) {
        this.kshort = kshort;
    }

    public void setFirst1minCandleDate(Date first1minCandleDate) {
        this.first1minCandleDate = first1minCandleDate;
    }

    public void setMinPriceIncrement(QuotationDto minPriceIncrement) {
        this.minPriceIncrement = minPriceIncrement;
    }

    public void setOtcFlag(boolean otcFlag) {
        this.otcFlag = otcFlag;
    }

    public void setDshortClient(QuotationDto dshortClient) {
        this.dshortClient = dshortClient;
    }

    public void setKlong(QuotationDto klong) {
        this.klong = klong;
    }

    public void setDshort(QuotationDto dshort) {
        this.dshort = dshort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setCountryOfRiskName(String countryOfRiskName) {
        this.countryOfRiskName = countryOfRiskName;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public void setPlacementPrice(MoneyValueDto placementPrice) {
        this.placementPrice = placementPrice;
    }

    public void setAciValue(MoneyValueDto aciValue) {
        this.aciValue = aciValue;
    }

    public void setInitialNominal(MoneyValueDto initialNominal) {
        this.initialNominal = initialNominal;
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

    public void setIssueKind(String issueKind) {
        this.issueKind = issueKind;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public void setStateRegDate(Date stateRegDate) {
        this.stateRegDate = stateRegDate;
    }

    public void setIssueSizePlan(String issueSizePlan) {
        this.issueSizePlan = issueSizePlan;
    }

    public void setIssueSize(String issueSize) {
        this.issueSize = issueSize;
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

    public void setIsoCurrencyName(String isoCurrencyName) {
        this.isoCurrencyName = isoCurrencyName;
    }

    public String getAssetUid() {
        return assetUid;
    }

    public String getFigi() {
        return figi;
    }

    public QuotationDto getDshortMin() {
        return dshortMin;
    }

    public String getCountryOfRisk() {
        return countryOfRisk;
    }

    public int getLot() {
        return lot;
    }

    public String getUid() {
        return uid;
    }

    public List<String> getRequiredTests() {
        return requiredTests;
    }

    public boolean isBlockedTcaFlag() {
        return blockedTcaFlag;
    }

    public QuotationDto getDlong() {
        return dlong;
    }

    public QuotationDto getDlongClient() {
        return dlongClient;
    }

    public MoneyValueDto getNominal() {
        return nominal;
    }

    public boolean isSellAvailableFlag() {
        return sellAvailableFlag;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getFirst1dayCandleDate() {
        return first1dayCandleDate;
    }

    public BrandDto getBrand() {
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

    public boolean isForIisFlag() {
        return forIisFlag;
    }

    public String getPositionUid() {
        return positionUid;
    }

    public boolean isApiTradeAvailableFlag() {
        return apiTradeAvailableFlag;
    }

    public QuotationDto getDlongMin() {
        return dlongMin;
    }

    public boolean isShortEnabledFlag() {
        return shortEnabledFlag;
    }

    public QuotationDto getKshort() {
        return kshort;
    }

    public Date getFirst1minCandleDate() {
        return first1minCandleDate;
    }

    public QuotationDto getMinPriceIncrement() {
        return minPriceIncrement;
    }

    public boolean isOtcFlag() {
        return otcFlag;
    }

    public QuotationDto getDshortClient() {
        return dshortClient;
    }

    public QuotationDto getKlong() {
        return klong;
    }

    public QuotationDto getDshort() {
        return dshort;
    }

    public String getName() {
        return name;
    }

    public String getExchange() {
        return exchange;
    }

    public String getCountryOfRiskName() {
        return countryOfRiskName;
    }

    public String getIsin() {
        return isin;
    }

    public Date getCallDate() {
        return callDate;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public MoneyValueDto getPlacementPrice() {
        return placementPrice;
    }

    public MoneyValueDto getAciValue() {
        return aciValue;
    }

    public MoneyValueDto getInitialNominal() {
        return initialNominal;
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

    public String getIssueKind() {
        return issueKind;
    }

    public Date getPlacementDate() {
        return placementDate;
    }

    public Date getStateRegDate() {
        return stateRegDate;
    }

    public String getIssueSizePlan() {
        return issueSizePlan;
    }

    public String getIssueSize() {
        return issueSize;
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

    public String getIsoCurrencyName() {
        return isoCurrencyName;
    }
}
