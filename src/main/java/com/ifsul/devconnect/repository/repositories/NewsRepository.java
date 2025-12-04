package com.ifsul.devconnect.repository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsul.devconnect.repository.models.NewsEntity;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {
}
