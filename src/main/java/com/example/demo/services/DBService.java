package com.example.demo.services;

import com.example.demo.dto.InstrumentDto;
import com.example.demo.entity.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DBService {
    Page<Instrument> findAll(int page, String sort);
    Page<Instrument> findByBondIsNotEmpty(int page, String sort);
    Page<Instrument> findByShareIsNotEmpty(int page, String sort);
    Page<Instrument> findByCurrencyIsNotEmpty(int page, String sort);
    void saveAll(List<Instrument> instruments);
    Instrument dtoToEntity(InstrumentDto dto);
    String getSortBy();

}
