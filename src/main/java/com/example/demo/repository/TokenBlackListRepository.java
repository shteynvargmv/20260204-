package com.example.demo.repository;

import com.example.demo.entity.TokenBlackList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {
    List<TokenBlackList> findAll();
    TokenBlackList findFirstByToken(String token);
    @Transactional
    @Modifying
    Integer deleteByToken(String token);
    TokenBlackList save(String token);
}