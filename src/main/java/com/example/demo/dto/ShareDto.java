package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareDto {
    private String type;
    private String ipoDate;
    private String registryDate;
    private boolean divYieldFlag;
    private String issueKind;
    private String represIsin;
    private String primaryIndex;
    private String preferredShareType;
    private String nominalCurrency;
    private String placementDate;

    @JsonProperty("issueSize")
    private MoneyValueDto issueSize;

    @JsonProperty("issueSizePlan")
    private MoneyValueDto issueSizePlan;

    @JsonProperty("nominal")
    private MoneyValueDto nominal;

    @JsonProperty("totalFloat")
    private MoneyValueDto totalFloat;

    @JsonProperty("dividendRate")
    private MoneyValueDto dividendRate;
}