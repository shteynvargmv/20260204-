package com.example.demo.services;

import com.example.demo.dto.InstrumentsResponse;
import org.springframework.http.ResponseEntity;

public interface BrokerApiService {
    ResponseEntity<InstrumentsResponse> getAllBonds();
    ResponseEntity<InstrumentsResponse> getAllShares();
    ResponseEntity<InstrumentsResponse> getAllCurrencies();
}
