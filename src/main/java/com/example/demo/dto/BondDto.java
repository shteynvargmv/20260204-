package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BondDto {
    private boolean amortizationFlag;
    private boolean perpetualFlag;
    private boolean floatingCouponFlag;
    private boolean subordinatedFlag;
    private boolean collateralFlag;
    private boolean indexedNominalFlag;
    private boolean taxFreeFlag;
    private int couponQuantityPerYear;
    private String interestKind;
    private String returnCondition;
    private String borrowName;
    private String issueKind;
    private String nominalCurrency;
    private String maturityDate;
    private String placementDate;
    private String stateRegDate;

    @JsonProperty("nominal")
    private MoneyValueDto nominal;

    @JsonProperty("issueSize")
    private MoneyValueDto issueSize;

    @JsonProperty("issueSizePlan")
    private MoneyValueDto issueSizePlan;

    @JsonProperty("currentNominal")
    private MoneyValueDto currentNominal;

    @JsonProperty("placementPrice")
    private MoneyValueDto placementPrice;
}