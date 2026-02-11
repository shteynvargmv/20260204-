package com.example.demo.service;

import com.example.demo.entity.TokenBlackList;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.TokenBlackListRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TokenBlackListService{

    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;

    public Integer deleteByToken(String token) {
        return tokenBlackListRepository.deleteByToken(token);
    }

    public TokenBlackList logout(String token) {
        return tokenBlackListRepository.save(new TokenBlackList(token));
    }

    public TokenBlackList findFirstByToken(String token) {
        return tokenBlackListRepository.findFirstByToken(token);
    }
}