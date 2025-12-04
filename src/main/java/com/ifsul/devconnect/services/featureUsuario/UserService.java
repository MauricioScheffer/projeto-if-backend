package com.ifsul.devconnect.services.featureUsuario;

import java.util.List;
import com.ifsul.devconnect.repository.models.UserEntity;
import com.ifsul.devconnect.routes.dto.user.TokenResponse;

public interface UserService {
    List<UserEntity> getAll();

    TokenResponse login(String email, String senha);

    UserEntity getById(int id);

    UserEntity save(UserEntity user);

    void delete(int id);
}
