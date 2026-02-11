package com.example.demo.model;

import com.example.demo.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Token {
    private String token;
    private String loginResult;
    private Role role;

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public Token(String token, String loginResult, Role role) {
        this.token = token;
        this.loginResult = loginResult;
        this.role = role;
    }

    public Token(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getToken() {
        return token;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }
}
