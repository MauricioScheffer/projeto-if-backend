package com.ifsul.devconnect.repository.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ifsul.devconnect.repository.models.Tipo;
import com.ifsul.devconnect.repository.models.UserEntity;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

 
    List<UserEntity> findByTipo(Tipo tipo);
    //List<UserEntity> findProfessores();


}
