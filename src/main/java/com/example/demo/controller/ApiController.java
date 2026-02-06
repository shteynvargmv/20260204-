package com.example.demo.controller;

import com.example.demo.dto.InstrumentsResponse;
import com.example.demo.services.BrokerApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invest")
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

}
