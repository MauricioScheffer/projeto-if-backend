package com.ifsul.devconnect.services.featureNews;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifsul.devconnect.repository.models.NewsEntity;
import com.ifsul.devconnect.repository.repositories.NewsRepository;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public List<NewsEntity> listarTodas() {
        return newsRepository.findAll();
    }

    @Override
    public NewsEntity salvar(NewsEntity news) {
        return newsRepository.save(news);
    }

    @Override
    public NewsEntity buscarPorId(int id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public void deletar(int id) {
        newsRepository.deleteById(id);
    }
}