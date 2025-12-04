package com.ifsul.devconnect.services.featureUsuario;

import java.util.List;
import java.util.UUID;

import com.ifsul.devconnect.repository.models.UserEntity;
import com.ifsul.devconnect.routes.dto.user.TokenResponse;

public interface UserService {
    List<UserEntity> getAll();

    TokenResponse login(String email, String senha);

    UserEntity getById(UUID id);

    UserEntity save(UserEntity user);

    void delete(UUID id);
}
