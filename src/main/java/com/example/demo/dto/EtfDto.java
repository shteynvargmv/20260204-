package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EtfDto {
    private boolean ucitsFlag;
    private boolean leveragedFlag;
    private boolean rebalancingFlag;
    private boolean watermarkedFlag;
    private boolean divYieldFlag;
    private String releasedDate;
    private String description;
    private String primaryIndex;
    private String primaryIndexDescription;
    private String primaryIndexCompany;
    private String focusType;
    private String rebalancingPlan;
    private String rebalancingFreq;
    private String managementType;
    private String paymentType;
    private String inavCode;
    private String issueKind;
    private String nominalCurrency;
    private List<String> rebalancingDates;

    @JsonProperty("nominal")
    private MoneyValueDto nominal;

    @JsonProperty("fixedCommission")
    private MoneyValueDto fixedCommission;

    @JsonProperty("expenseCommission")
    private MoneyValueDto expenseCommission;

    @JsonProperty("performanceFee")
    private MoneyValueDto performanceFee;

    @JsonProperty("totalExpense")
    private MoneyValueDto totalExpense;

    @JsonProperty("buyPremium")
    private MoneyValueDto buyPremium;

    @JsonProperty("sellDiscount")
    private MoneyValueDto sellDiscount;

    @JsonProperty("numShare")
    private MoneyValueDto numShare;

    @JsonProperty("hurdleRate")
    private MoneyValueDto hurdleRate;

    @JsonProperty("taxRate")
    private MoneyValueDto taxRate;

    @JsonProperty("primaryIndexTrackingError")
    private MoneyValueDto primaryIndexTrackingError;

    @JsonProperty("indexRecoveryPeriod")
    private MoneyValueDto indexRecoveryPeriod;
}