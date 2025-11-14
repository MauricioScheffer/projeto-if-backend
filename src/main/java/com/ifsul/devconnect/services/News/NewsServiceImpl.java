package com.ifsul.devconnect.services.News;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsul.devconnect.services.News.NewsService;
import com.ifsul.devconnect.config.exception_handler.DomainException;
import com.ifsul.devconnect.repository.models.NewsEntity;
import com.ifsul.devconnect.repository.repositories.NewsRepository;

@Service
public class NewsServiceImpl implements NewsService {

    
    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<NewsEntity> getAll() {
        return newsRepository.findAll();
    }

    @Override

    public NewsEntity save(NewsEntity news) {
        if (news.getTitulo() == null || news.getTitulo().trim().isEmpty()) {
            throw new DomainException(
                "Título inválido",
                "O título da notícia não pode estar vazio.",
                HttpStatus.BAD_REQUEST
            );
        }
    if (news.getResumo() != null && news.getResumo().length() > 300) {
        throw new DomainException(
            "Resumo muito longo",
            "O resumo da notícia não pode ultrapassar 300 caracteres.",
            HttpStatus.BAD_REQUEST
        );
    }

    
    if (news.getConteudo() == null || news.getConteudo().trim().isEmpty()) {
        throw new DomainException(
            "Conteúdo inválido",
            "O conteúdo da notícia não pode ser nulo ou vazio.",
            HttpStatus.BAD_REQUEST
        );
    }

    
    try {
        new java.text.SimpleDateFormat("dd/MM/yyyy").parse(news.getData());
    } catch (Exception e) {
        throw new DomainException(
            "Data inválida",
            "A data da notícia deve estar no formato dd/MM/yyyy.",
            HttpStatus.BAD_REQUEST
        );
    }

        return newsRepository.save(news);
    }



    @Override
    public NewsEntity getById(int id) {
        return newsRepository.findById(id)
            .orElseThrow(() -> new DomainException(
                "Notícia não encontrada",
                "A notícia com o ID informado não foi encontrada.",
                HttpStatus.NOT_FOUND
            ));
    }


    @Override
    public void delete(int id) {
        if (!newsRepository.existsById(id)) {
            throw new DomainException(
                "Notícia não encontrada",
                "Não foi possível deletar: notícia com o ID informado não existe.",
                HttpStatus.NOT_FOUND
            );
        }
        newsRepository.deleteById(id);
    }


    
    

}