package com.example.demo.service.implementations;


import com.example.demo.dto.response.*;
import com.example.demo.model.Cache;
import com.example.demo.model.CacheStat;
import com.example.demo.service.BrokerApiService;
import com.example.demo.utils.RestUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TBankApiService implements BrokerApiService {
    @Autowired
    private Cache cache;
    @Autowired
    private ObjectFactory<CacheStat> cacheStatFactory;
    @Autowired
    private RestUtils restUtils;


    @Override
    public ResponseEntity<InstrumentsResponse> getAllBonds() {
        return restUtils.getAllBonds();
    }
    @Override
    public ResponseEntity<InstrumentsResponse> getAllShares() {
        return restUtils.getAllShares();
    }
    @Override
    public ResponseEntity<InstrumentsResponse> getAllCurrencies() {
        return restUtils.getAllCurrencies();
    }
    @Override
    public ResponseEntity<LastPricesResponse> getLastPrices(List<String> uids) {
        return restUtils.getLastPrices(uids);
    }
    @Override
    public ResponseEntity<LastPricesResponse> getLastPrices(String uid) {
        return this.getLastPrices(Arrays.asList(uid));
    }
    @Override
    public ResponseEntity<InstrumentResponse> getShareByUid(String uid) {
        return restUtils.getShareByUid(uid);
    }
    @Override
    public ResponseEntity<InstrumentResponse> getBondByUid(String uid) {
        return restUtils.getBondByUid(uid);
    }
    @Override
    public ResponseEntity<InstrumentResponse> getCurrencyByUid(String uid) {
        return restUtils.getCurrencyByUid(uid);
    }

    @Override
    public ResponseEntity<AssetResponse> getAssetById(String id) {
        return restUtils.getAssetByUid(id);
    }

    @Override
    public ResponseEntity<AssetsResponse> getAllAssetsBonds() {
        return restUtils.getAllAssetsBonds();
    }

    @Override
    public ResponseEntity<AssetsResponse> getAllAssetsShares() {
        return restUtils.getAllAssetsShares();
    }

    @Override
    public ResponseEntity<AssetsResponse> getAllAssetsCurrencies() {
        return restUtils.getAllAssetsCurrencies();
    }

}
