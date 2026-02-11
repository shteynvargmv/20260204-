package com.example.demo.controller;

import com.example.demo.dto.InstrumentsResponse;
import com.example.demo.dto.LastPricesResponse;
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
}
