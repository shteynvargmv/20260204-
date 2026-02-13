package com.example.demo.utils;


import com.example.demo.dto.InstrumentType;
import com.example.demo.dto.request.AssetRequest;
import com.example.demo.dto.request.AssetsRequest;
import com.example.demo.dto.request.InstrumentsRequest;
import com.example.demo.dto.request.LastPricesRequest;
import com.example.demo.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class RestUtils {
    @Autowired
    @Qualifier("restTemplateLong")
    private RestTemplate restTemplateLong;
    @Autowired
    Environment env;

    public ResponseEntity<InstrumentsResponse> getAllBonds() {
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.InstrumentsService/Bonds";

        InstrumentsRequest requestBody = new InstrumentsRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));

        HttpEntity<InstrumentsRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<InstrumentsResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                InstrumentsResponse.class
        );
        return result;
    }


    public ResponseEntity<InstrumentsResponse> getAllShares() {
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.InstrumentsService/Shares";

        InstrumentsRequest requestBody = new InstrumentsRequest();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        HttpEntity<InstrumentsRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<InstrumentsResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                InstrumentsResponse.class
        );
        return result;
    }

    public ResponseEntity<LastPricesResponse> getLastPrices(List<String> uids){
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.MarketDataService/GetLastPrices";

        LastPricesRequest requestBody = new LastPricesRequest();
        requestBody.setInstrumentId(uids);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        HttpEntity<LastPricesRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<LastPricesResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                LastPricesResponse.class
        );
        return result;
    }

    public ResponseEntity<InstrumentResponse> getBondByUid(String uid){
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.InstrumentsService/BondBy";

        InstrumantByUidRequest requestBody = new InstrumantByUidRequest();
        requestBody.setId(uid);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        HttpEntity<InstrumantByUidRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<InstrumentResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                InstrumentResponse.class
        );
        return result;
    }

    public ResponseEntity<InstrumentResponse> getCurrencyByUid(String uid){
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.InstrumentsService/CurrencyBy";

        InstrumantByUidRequest requestBody = new InstrumantByUidRequest();
        requestBody.setId(uid);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        HttpEntity<InstrumantByUidRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<InstrumentResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                InstrumentResponse.class
        );
        return result;
    }

    public ResponseEntity<InstrumentResponse> getShareByUid(String uid){
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.InstrumentsService/ShareBy";

        InstrumantByUidRequest requestBody = new InstrumantByUidRequest();
        requestBody.setId(uid);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        HttpEntity<InstrumantByUidRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<InstrumentResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                InstrumentResponse.class
        );
        return result;
    }

    public ResponseEntity<InstrumentsResponse> getAllCurrencies() {
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.InstrumentsService/Currencies";

        InstrumentsRequest requestBody = new InstrumentsRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        HttpEntity<InstrumentsRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<InstrumentsResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                InstrumentsResponse.class
        );
        return result;
    }

    public ResponseEntity<AssetResponse> getAssetByUid(String id) {
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.InstrumentsService/GetAssetBy";

        AssetRequest requestBody = new AssetRequest(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        HttpEntity<AssetRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<AssetResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                AssetResponse.class
        );
        return result;
    }

    public ResponseEntity<AssetsResponse> getAllAssetsBonds() {
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.InstrumentsService/GetAssets";

        AssetsRequest requestBody = new AssetsRequest(InstrumentType.INSTRUMENT_TYPE_BOND);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        HttpEntity<AssetsRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<AssetsResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                AssetsResponse.class
        );
        return result;
    }

    public ResponseEntity<AssetsResponse> getAllAssetsShares() {
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.InstrumentsService/GetAssets";

        AssetsRequest requestBody = new AssetsRequest(InstrumentType.INSTRUMENT_TYPE_SHARE);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        HttpEntity<AssetsRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<AssetsResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                AssetsResponse.class
        );
        return result;
    }

    public ResponseEntity<AssetsResponse> getAllAssetsCurrencies() {
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.InstrumentsService/GetAssets";

        AssetsRequest requestBody = new AssetsRequest(InstrumentType.INSTRUMENT_TYPE_CURRENCY);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        HttpEntity<AssetsRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<AssetsResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                AssetsResponse.class
        );
        return result;
    }
}