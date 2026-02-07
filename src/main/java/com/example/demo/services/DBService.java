package com.example.demo.services;

import com.example.demo.dto.FilterRequest;
import com.example.demo.dto.InstrumentDto;
import com.example.demo.dto.LastPriceDto;
import com.example.demo.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface DBService {
    Page<Instrument> findAll(int page, Sort sort);
    Page<Instrument> findByBondIsNotEmpty(int page, Sort sort);
    Page<Instrument> findByShareIsNotEmpty(int page, Sort sort);
    Page<Instrument> findByCurrencyIsNotEmpty(int page, Sort sort);
    void saveAll(List<Instrument> instruments);
    Instrument dtoToEntity(InstrumentDto dto, List<LastPriceDto> prices);
    List<String> findSectorsAll();
    Page<Instrument> findAllBySectors(List<String> sectors, List<String> parameters, int page, Sort sort);
    Page<Instrument> findShareBySectors(List<String> sectors, List<String> parameters, int page, Sort sort);
    Page<Instrument> findBondBySectors(List<String> sectors, List<String> parameters, int page, Sort sort);
    Page<Instrument> findInstruments(String type, Filter filter, String pageNum, Sort sort);
    
}
