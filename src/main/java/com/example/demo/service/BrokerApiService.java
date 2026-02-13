package com.example.demo.service;

import com.example.demo.dto.response.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BrokerApiService {
    ResponseEntity<InstrumentsResponse> getAllBonds();

    ResponseEntity<InstrumentsResponse> getAllShares();

    ResponseEntity<InstrumentsResponse> getAllCurrencies();

    ResponseEntity<LastPricesResponse> getLastPrices(List<String> uids);

    ResponseEntity<LastPricesResponse> getLastPrices(String uid);

    ResponseEntity<InstrumentResponse> getShareByUid(String uid);

    ResponseEntity<InstrumentResponse> getBondByUid(String uid);

    ResponseEntity<InstrumentResponse> getCurrencyByUid(String uid);

    ResponseEntity<AssetResponse> getAssetById(String id);

    ResponseEntity<AssetsResponse> getAllAssetsBonds();

    ResponseEntity<AssetsResponse> getAllAssetsShares();

    ResponseEntity<AssetsResponse> getAllAssetsCurrencies();
}
