package com.ifsul.devconnect.repository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsul.devconnect.repository.models.HomeEntity;

@Repository
public interface IHomeRepository extends JpaRepository<HomeEntity, Long> {
    HomeEntity findById(long id);
}
