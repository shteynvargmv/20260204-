package com.example.demo.service;

import com.example.demo.dto.InstrumentsResponse;
import com.example.demo.dto.LastPricesResponse;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface BrokerApiService {
    ResponseEntity<InstrumentsResponse> getAllBonds();
    ResponseEntity<InstrumentsResponse> getAllShares();
    ResponseEntity<InstrumentsResponse> getAllCurrencies();
    ResponseEntity<LastPricesResponse> getLastPrices(List<String> uids);
}
