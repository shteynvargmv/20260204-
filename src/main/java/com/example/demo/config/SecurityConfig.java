package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.demo.jwt.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authorize) -> authorize
//                .requestMatchers("/auth/login",
//                        "/auth/register",
//                        "/invest/home",
//                        "invest/sign-in",
//                        "invest/sign-up",
//                        "/css/**",
//                        "/js/**",
//                        "/fonts/**",
//                        "/img/**").permitAll()

//                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/invest/catalog", "/invest/catalog/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/auth/logout").authenticated()
//                .anyRequest().authenticated()
                            .anyRequest().permitAll() //<--/invest/logout/success

            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
