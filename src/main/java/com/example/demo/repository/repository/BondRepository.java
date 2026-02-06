package com.example.demo.repository.repository;

import com.example.demo.entity.Bond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BondRepository extends JpaRepository<Bond,String> {
    List<Bond> findAll();
}
