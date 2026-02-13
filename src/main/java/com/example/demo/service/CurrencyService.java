package com.example.demo.service;

import com.example.demo.entity.CurrencySymbol;
import com.example.demo.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    public CurrencySymbol findByCode(String code){
        Optional<CurrencySymbol> currencySymbolOpt = currencyRepository.findByCode(code);
        if (currencySymbolOpt.isPresent()){
            return currencySymbolOpt.get();
        }
        return null;
    }
    public void saveAll(List<CurrencySymbol> currencies){
        currencyRepository.saveAll(currencies);
    }
}
