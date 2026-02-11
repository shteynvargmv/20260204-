package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
public class TokenBlackList{
    @Id
    @Column(unique = true, nullable = false)
    private String token;

    public TokenBlackList(String token) {
        this.token = token;
    }

    public TokenBlackList() {

    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

}
