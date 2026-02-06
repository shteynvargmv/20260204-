package com.example.demo.repository.repository;

import com.example.demo.entity.Instrument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface InstrumentRepository extends JpaRepository<Instrument,String> {
    Page<Instrument> findAll(Pageable pageable);
    Page<Instrument> findByBondIsNotNull(Pageable pageable);
    Page<Instrument> findByShareIsNotNull(Pageable pageable);
    Page<Instrument> findByCurrencyIsNotNull(Pageable pageable);
}
