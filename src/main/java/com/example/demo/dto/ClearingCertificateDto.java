package com.example.demo.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClearingCertificateDto {
    private String nominalCurrency;

    @JsonProperty("nominal")
    private MoneyValueDto nominal;
}