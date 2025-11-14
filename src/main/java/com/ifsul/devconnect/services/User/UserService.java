package com.ifsul.devconnect.services.User;

import java.util.List;

import com.ifsul.devconnect.routes.dto.user.UserDTO;
import com.ifsul.devconnect.repository.models.Tipo;
import com.ifsul.devconnect.repository.models.UserEntity;
import com.ifsul.devconnect.routes.dto.user.UserCreateDTO;


public interface UserService {
    List<UserDTO> getAll();
    UserDTO  getById(int id);
    UserDTO save(UserCreateDTO user);
    void delete(int id);
    List<UserDTO> findByTipo(Tipo tipo);
    List<UserDTO> findProfessores();


}




