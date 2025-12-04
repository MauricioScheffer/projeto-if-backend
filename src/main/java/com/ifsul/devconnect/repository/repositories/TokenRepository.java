package com.ifsul.devconnect.repository.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifsul.devconnect.repository.models.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {

}
