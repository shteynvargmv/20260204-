package com.example.demo.service;

import com.example.demo.dto.AssetDto;
import com.example.demo.dto.InstrumentDto;
import com.example.demo.dto.LastPriceDto;
import com.example.demo.entity.*;
import com.example.demo.model.Filter;
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
    Instrument dtoToEntity(InstrumentDto dto, List<LastPriceDto> prices, AssetDto asset);
    Instrument dtoToEntity(InstrumentDto dto, List<LastPriceDto> prices, List<AssetDto> assets);
    List<String> findSectorsAll();
    Page<Instrument> findAllBy(List<String> sectors, List<String> parameters, String searchValue, int page, Sort sort);
    Page<Instrument> findShareBy(List<String> sectors, List<String> parameters, String searchValue, int page, Sort sort);
    Page<Instrument> findBondBy(List<String> sectors, List<String> parameters, String searchValue, int page, Sort sort);
    Page<Instrument> findCurrencyBy(String searchValue, int page, Sort sort);
    Page<Instrument> findInstruments(String type, Filter filter, String pageNum, Sort sort);
    Instrument findFirstByUid(String uid);
    Instrument save(Instrument instrument);
}
