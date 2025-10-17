package com.ifsul.devconnect.services.featureNews;

import java.util.List;
import com.ifsul.devconnect.repository.models.NewsEntity;

public interface NewsService {
    List<NewsEntity> listarTodas();
    NewsEntity buscarPorId(int id);
    NewsEntity salvar(NewsEntity noticia);
    void deletar(int id);
}
