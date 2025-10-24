package com.ifsul.devconnect.repository.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ifsul.devconnect.repository.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
}
