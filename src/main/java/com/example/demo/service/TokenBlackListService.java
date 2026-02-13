package com.example.demo.service;

import com.example.demo.entity.TokenBlackList;
import com.example.demo.repository.TokenBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenBlackListService{

    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;

    public Integer deleteByToken(String token) {
        return tokenBlackListRepository.deleteByToken(token);
    }

    public Boolean logout(String token) {
        TokenBlackList tokenBlackListNew = tokenBlackListRepository.save(new TokenBlackList(token));
        return tokenBlackListNew.getToken().equals(token);
    }

    public TokenBlackList findFirstByToken(String token) {
        return tokenBlackListRepository.findFirstByToken(token);
    }
}