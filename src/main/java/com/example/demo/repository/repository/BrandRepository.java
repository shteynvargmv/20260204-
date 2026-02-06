package com.example.demo.repository.repository;

import com.example.demo.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    List<Brand> findAll();
}
