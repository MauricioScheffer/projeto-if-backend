package com.ifsul.devconnect.services.featureUsuario;

import java.util.List;
import com.ifsul.devconnect.repository.models.UserEntity;

public interface UserService {
    List<UserEntity> getAll();
    UserEntity getById(int id);
    UserEntity save(UserEntity user);
    void delete(int id);
}
