package com.ifsul.devconnect.services.featureNews;

import java.util.List;
import com.ifsul.devconnect.repository.models.NewsEntity;

public interface NewsService {
    List<NewsEntity> getAll();
    NewsEntity getById(int id);
    NewsEntity save(NewsEntity noticia);
    void delete(int id);
}
