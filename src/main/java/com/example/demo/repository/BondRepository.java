package com.example.demo.repository;

import com.example.demo.entity.Bond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BondRepository extends JpaRepository<Bond,String> {
    List<Bond> findAll();
    @Query(value="select b.sector " +
            "from Bond b where b.sector != null and b.sector != ''")
    List<String> findSectorsAll();
}
