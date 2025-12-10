package com.ifsul.devconnect.config;

import com.ifsul.devconnect.config.token.GenerateToken;
import com.ifsul.devconnect.repository.models.TokenEntity;
import com.ifsul.devconnect.repository.models.UserEntity;
import com.ifsul.devconnect.repository.repositories.TokenRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) {

        try {
            String path = request.getRequestURI();

            System.out.println(path);
            if (path.equals("/users/login") || path.equals("/users/new")) {
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                String token = authHeader.substring(7);
                String tokenId = GenerateToken.extractId(token);
                Optional<TokenEntity> tokenEntityOpt = tokenRepository.findById(UUID.fromString(tokenId));

                if (tokenEntityOpt.isPresent()) {
                    TokenEntity tokenEntity = tokenEntityOpt.get();

                    if (tokenEntity.getExpireAt().isAfter(LocalDateTime.now())) {

                        UserEntity user = tokenEntity.getUser();

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of());

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
                filterChain.doFilter(request, response);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}