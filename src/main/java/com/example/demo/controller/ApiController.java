package com.example.demo.controller;

import com.example.demo.dto.response.AssetResponse;
import com.example.demo.dto.response.InstrumentResponse;
import com.example.demo.dto.response.InstrumentsResponse;
import com.example.demo.dto.response.LastPricesResponse;
import com.example.demo.service.BrokerApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    @Qualifier("tbank")
    BrokerApiService tbankApiService;

    @PostMapping("/bonds")
    public ResponseEntity<InstrumentsResponse> getAllBonds() {
        return tbankApiService.getAllBonds();
    }

    @PostMapping("/shares")
    public ResponseEntity<InstrumentsResponse> getAllShares() {
        return tbankApiService.getAllShares();
    }

    @PostMapping("/currencies")
    public ResponseEntity<InstrumentsResponse> getAllCurrencies() {
        return tbankApiService.getAllCurrencies();
    }

    @PostMapping("/prices/last")
    public ResponseEntity<LastPricesResponse> getLastPrices(@RequestBody List<String> uids) {
        return tbankApiService.getLastPrices(uids);
    }

    @PostMapping("/share-by-id")
    public ResponseEntity<InstrumentResponse> getShareById(@RequestBody String uid) {
        return tbankApiService.getShareByUid(uid);
    }

    @PostMapping("/bond-by-id")
    public ResponseEntity<InstrumentResponse> getBondById(@RequestBody String uid) {
        return tbankApiService.getBondByUid(uid);
    }

    @PostMapping("/currency-by-id")
    public ResponseEntity<InstrumentResponse> getCurrencyById(@RequestBody String uid) {
        return tbankApiService.getCurrencyByUid(uid);
    }

    @PostMapping("/asset-by-id")
    public ResponseEntity<AssetResponse> getAssetById(@RequestBody String id) {
        return tbankApiService.getAssetById(id);
    }

}
