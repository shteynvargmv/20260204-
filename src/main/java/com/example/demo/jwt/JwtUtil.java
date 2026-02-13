package com.example.demo.jwt;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TokenBlackListService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Autowired
    TokenBlackListService tokenBlackListService;

    @Autowired
    UserRepository userRepository;

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public String extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (ExpiredJwtException e) {
            return e.getClaims().getExpiration();
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private Boolean isRefreshTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Boolean isTokenExpired(String token) {
//        System.out.println("isTokenExpired-JwtUtil");
        try {
            Date expiration = extractExpiration(token);
//            System.out.println("time " + expiration);
            boolean expired = expiration.before(new Date());

            if (expired) {
//                System.out.println("Expired-true");
                final String username = extractUsername(token);
//                System.out.println("Username from expired token: " + username);

                Optional<User> userOpt = userRepository.findByUsername(username);
                if (userOpt.isPresent()) {
//                    System.out.println("Present-true");
                    User user = userOpt.get();
                    String refreshToken = user.getRefreshToken();

//                    System.out.println("Refresh token exists: " + (refreshToken != null));
                    if (refreshToken != null && !isRefreshTokenExpired(refreshToken)) {
//                        System.out.println("refresh - not expired");
                        token = generateToken(username);
                        user.setToken(token);
                        userRepository.save(user);
                        System.out.println("new token generated");
                        // Здесь нужно как-то вернуть новый токен клиенту
                    } else {
//                        System.out.println("Refresh token expired or not found");
                    }
                } else {
//                    System.out.println("User not found in DB");
                }
            } else {
//                System.out.println("Token not expired yet");
            }
            expiration = extractExpiration(token);
            return expired = expiration.before(new Date());

        } catch (Exception e) {
//            System.out.println("Error checking token expiration: " + e.getMessage());
            return true;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            boolean isBlacklisted = tokenBlackListService.findFirstByToken(token) != null;
            boolean isExpired = isTokenExpired(token);

//            System.out.println("Validate token - username: " + username);
//            System.out.println("Validate token - blacklisted: " + isBlacklisted);
//            System.out.println("Validate token - expired: " + isExpired);

            return (username.equals(userDetails.getUsername())
                    && !isBlacklisted
                    && !isExpired);
        } catch (Exception e) {
//            System.out.println("Token validation error: " + e.getMessage());
            return false;
        }
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60)) // 1 минута
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateRefreshToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, userName);
    }

    private String createRefreshToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30)) // 30 дней
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValidForRefresh(String token) {
        try {
            final String username = extractUsername(token);
            return username != null && !username.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public String refreshToken(String expiredToken) {
        try {
            String username = extractUsername(expiredToken);
            if (username != null) {
                return generateToken(username);
            }
            return null;
        } catch (Exception e) {
//            System.out.println("Error refreshing token: " + e.getMessage());
            return null;
        }
    }

    public void delCookie(HttpServletResponse response) {
        Cookie authCookie = new Cookie("token", null);
        authCookie.setHttpOnly(true);
        authCookie.setSecure(false);
        authCookie.setPath("/");
        authCookie.setMaxAge(0);
        response.addCookie(authCookie);
    }

    public void setCookie(HttpServletRequest request,
                          HttpServletResponse response) {
        String token = extractToken(request);
//        System.out.println("token set cookie " + token);
        if (token != null && !token.trim().isEmpty()) {
            String username = extractUsername(token);
            Optional<User> userOpt = userRepository.findByUsername(username);
            userOpt.ifPresent(user -> setCookie(user, response));
        }
    }

    public void setCookie(User user,
                          HttpServletResponse response) {
        Cookie authCookie = new Cookie("token", user.getToken());
        authCookie.setHttpOnly(true);
        authCookie.setSecure(false); // true для HTTPS
        authCookie.setPath("/");
        authCookie.setMaxAge(24 * 60 * 60); // 24 часа
        response.addCookie(authCookie);
    }
}
//package com.example.demo.jwt;
//
//import com.example.demo.entity.User;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.service.TokenBlackListService;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.*;
//import java.util.function.Function;
//
//@Component
//public class JwtUtil {
//
//    @Autowired
//    TokenBlackListService tokenBlackListService;
//
//    @Autowired
//    UserRepository userRepository;
//
//    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
//
//    private Set<String> deleted;
//
//    public JwtUtil() {
//        this.deleted = new HashSet<>();
//    }
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Boolean isRefreshTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Boolean isTokenExpired(String token) {
//        System.out.println("isTokenExpired-JwtUtil");
//        if (extractExpiration(token).before(new Date())) { //истек
//            final String username = extractUsername(token);
//            System.out.println("Expired-true");
//            Optional<User> userOpt = userRepository.findByUsername(username);
//            if (userOpt.isPresent()) {
//                System.out.println("Present-true");
//                User user = userOpt.get();
//                String refreshToken = user.getRefreshToken();
//                if (!isRefreshTokenExpired(refreshToken)) {
//                    System.out.println("refresh - not expired");
//                    String newToken = generateToken(username);
//                    user.setToken(newToken);
//                    userRepository.save(user);
//                    System.out.println("new token " + token);
//                }
//            }
//        }
//        return extractExpiration(token).before(new Date());
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())
//                && tokenBlackListService.findFirstByToken(token) == null
//                && !isTokenExpired(token));
//    }
//
//    public String generateToken(String userName) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userName);
//    }
//
//    private String createToken(Map<String, Object> claims, String userName) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userName)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
//    }
//
//    public String generateRefreshToken(String userName) {
//        Map<String, Object> claims = new HashMap<>();
//        return createRefreshToken(claims, userName);
//    }
//
//    private String createRefreshToken(Map<String, Object> claims, String userName) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userName)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
//    }
//
//    private Key getSignKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//}
