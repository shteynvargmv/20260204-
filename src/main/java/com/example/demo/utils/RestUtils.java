package com.example.demo.utils;


import com.example.demo.dto.InstrumentsResponse;
import com.example.demo.dto.InstrumentsRequest;
import com.example.demo.dto.LastPricesRequest;
import com.example.demo.dto.LastPricesResponse;
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

        System.out.println(url);
        InstrumentsRequest requestBody = new InstrumentsRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        System.out.println(env.getProperty("tbank.token"));

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

        System.out.println(url);
        InstrumentsRequest requestBody = new InstrumentsRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        System.out.println(env.getProperty("tbank.token"));

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

        System.out.println(url);
        LastPricesRequest requestBody = new LastPricesRequest();
        requestBody.setInstrumentId(uids);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        System.out.println(env.getProperty("tbank.token"));

        HttpEntity<LastPricesRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<LastPricesResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                LastPricesResponse.class
        );
        return result;
    }

    public ResponseEntity<InstrumentsResponse> getAllCurrencies() {
        String url = env.getProperty("tbank.api.base.url") +
                "/tinkoff.public.invest.api.contract.v1.InstrumentsService/Currencies";

        System.out.println(url);
        InstrumentsRequest requestBody = new InstrumentsRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", env.getProperty("tbank.token")));
        System.out.println(env.getProperty("tbank.token"));

        HttpEntity<InstrumentsRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<InstrumentsResponse> result = restTemplateLong.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                InstrumentsResponse.class
        );
        return result;
    }

//    public ResponseEntity<CoinPriceApiResponse> price(List<String> idsList) {
//        ResponseEntity<CoinApiResponse> response;
//        CoinPriceApiResponse result = new CoinPriceApiResponse();
//
//        for (String id : idsList) {
//            String url = env.getProperty("coin.api.base.url") + "coins/" + id;
//            response = restTemplateLong.getForEntity(url, CoinApiResponse.class);
//            if (response.getStatusCode().is2xxSuccessful()) {
//                CoinApiResponse coin = response.getBody();
//                if (coin != null) {
//                    result.add(id, coin.getSymbol(), coin.getName(), coin.getMarketData().getCurrentPrice());
//                }
//            }
//        }
//
//        if (!result.isEmpty()) {
//            return ResponseEntity.ok(result);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    public ResponseEntity<TopCoinApiResponse[]> top(Integer limit) {
//        String url = env.getProperty("coin.api.base.url") +
//                "coins/markets?vs_currency=usd&order=market_cap_desc&per_page=" + limit + "&page=1";
//        return restTemplateLong.getForEntity(url, TopCoinApiResponse[].class);
//    }
//
//    public ResponseEntity<CoinApiResponse> search(String query) {
//        String url = env.getProperty("coin.api.base.url") + "coins/" + query;
//        return restTemplateLong.getForEntity(url, CoinApiResponse.class);
//    }
//
//    public ResponseEntity<CryptoCompareResponse> compare(List<String> idsList) {
//        ResponseEntity<CoinApiResponse> response;
//
//        CryptoCompareResponse result = new CryptoCompareResponse();
//        for (String id : idsList) {
//            String url = env.getProperty("coin.api.base.url") + "coins/" + id;
//            response = restTemplateLong.getForEntity(url, CoinApiResponse.class);
//            if (response.getStatusCode().is2xxSuccessful()) {
//                CoinApiResponse coin = response.getBody();
//                if (coin != null) {
//                    result.add(coin);
//                }
//            }
//        }
//
//        if (!result.getCryptos().isEmpty()) {
//            return ResponseEntity.ok(result);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
}