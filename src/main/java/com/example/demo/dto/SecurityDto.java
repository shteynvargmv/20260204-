package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityDto {
    private String isin;
    private String type;

    @JsonProperty("share")
    private ShareDto share;

    @JsonProperty("bond")
    private BondDto bond;

    @JsonProperty("etf")
    private EtfDto etf;

    @JsonProperty("sp")
    private SpDto sp;

    @JsonProperty("clearingCertificate")
    private ClearingCertificateDto clearingCertificate;
}