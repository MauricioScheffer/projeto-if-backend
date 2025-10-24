package com.ifsul.devconnect.routes.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.ifsul.devconnect.repository.models.NewsEntity;
import com.ifsul.devconnect.services.featureNews.NewsService;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // Retorna todas as notícias (resumidas)
    @GetMapping
    public List<NewsEntity> listar() {
        return newsService.listarTodas();
    }

    // Retorna uma notícia específica
    @GetMapping("/{id}")
    public NewsEntity buscarPorId(@PathVariable int id) {
        return newsService.buscarPorId(id);
    }

    // Cria uma nova notícia
    @PostMapping
    public NewsEntity criar(@RequestBody NewsEntity noticia) {
        return newsService.salvar(noticia);
    }

    // Deleta uma notícia
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable int id) {
        newsService.deletar(id);
    }
}
