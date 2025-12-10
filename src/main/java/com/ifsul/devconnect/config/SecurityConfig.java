package com.ifsul.devconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    public TokenFilter tokenFilter;

    @PostConstruct
    public void init() {
        System.out.println(">>> SecurityConfig: BEAN FOI CRIADO!");
    }

    public SecurityConfig(TokenFilter tokenFilter) {
        System.out.println(">>> SecurityConfig carregado");
        System.out.println(">>> TokenFilter injetado: " + tokenFilter);

        this.tokenFilter = tokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("Configuring security filter chain...");
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/users/login", "/users/new", "/news").permitAll()
                .anyRequest().authenticated()).httpBasic(basic -> basic.disable()).formLogin(f -> f.disable());

        http.addFilterBefore(tokenFilter,
                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
