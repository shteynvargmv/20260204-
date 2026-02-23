package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpDto {
    private String basicAsset;
    private String borrowName;
    private String issueKind;
    private String logicPortfolio;
    private String maturityDate;
    private String placementDate;
    private String nominalCurrency;

    @JsonProperty("nominal")
    private MoneyValueDto nominal;

    @JsonProperty("issueSize")
    private MoneyValueDto issueSize;

    @JsonProperty("issueSizePlan")
    private MoneyValueDto issueSizePlan;

    @JsonProperty("safetyBarrier")
    private MoneyValueDto safetyBarrier;
}
