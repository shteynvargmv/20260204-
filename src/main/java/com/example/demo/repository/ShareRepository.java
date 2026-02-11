package com.example.demo.repository;

import com.example.demo.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShareRepository extends JpaRepository<Share,String> {
    List<Share> findAll();
    @Query(value="select s.sector " +
            "from Share s where s.sector != null and s.sector != ''")
    List<String> findSectorsAll();
}
