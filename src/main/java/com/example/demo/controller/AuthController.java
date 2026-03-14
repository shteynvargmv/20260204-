package com.example.demo.controller;

import com.example.demo.model.Token;
import com.example.demo.service.CacheService;
import com.example.demo.service.entservice.TokenBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.request.AuthRequest;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.service.entservice.UserService;
import com.example.demo.dto.response.RegisterResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenBlackListService tokenBlackListService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("simple")
    private CacheService cacheService;

    @PostMapping("/login")
    @ResponseBody
    public Token authenticateAndGetToken(@RequestBody AuthRequest authRequest,
                                         HttpServletRequest request,
                                         HttpServletResponse response,
                                         Model model) {
        try {
            userService.loadUserByUsername(authRequest.getUsername());
        } catch (UsernameNotFoundException e){
            jwtUtil.delTokenCookie(response);
            return new Token(e.getMessage());
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                String token = jwtUtil.generateToken(authRequest.getUsername());
                String refreshToken = jwtUtil.generateRefreshToken(authRequest.getUsername());
                User user = userService.findByUsername(authRequest.getUsername());
                tokenBlackListService.deleteByToken(user.getToken());
                user.setToken(token);
                user.setRefreshToken(refreshToken);
                user = userService.save(user);
                jwtUtil.setCookie(user,response);
                return new Token(token,"Вы успешно зарегистрированы", user.getRole(), user.getUsername());
            } else {
                jwtUtil.delTokenCookie(response);
                return new Token("Неверный логин или пароль");
            }
        } catch (AuthenticationException e){
            jwtUtil.delTokenCookie(response);
            return new Token("Неверный логин или пароль");
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public RegisterResponse register(@RequestBody User user,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        jwtUtil.setCookie(request,response);
        return userService.register(user);
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<String> logout(HttpServletRequest request,
                                         HttpServletResponse response) {
        String token = jwtUtil.extractToken(request);
        if (token != null && !token.trim().isEmpty()) {
            String username = jwtUtil.extractUsername(token);
            User user = userService.findByUsername(username);
            if (user != null) {
                if (tokenBlackListService.logout(user.getToken()) &&
                    userService.delRefreshToken(user)) {
                    jwtUtil.delTokenCookie(response);
                    jwtUtil.delFavCookie(request,response);
                    cacheService.delAll();
                    return ResponseEntity.ok("Успешно выполнено");
                }
            }
        }
        return ResponseEntity.notFound().build();
    }
}

