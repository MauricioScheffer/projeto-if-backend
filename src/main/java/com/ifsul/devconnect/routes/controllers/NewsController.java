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

    
    @GetMapping
    public List<NewsEntity> getAll() {
        return newsService.getAll();
    }

    
    @GetMapping("/{id}")
    public NewsEntity getById(@PathVariable int id) {
        return newsService.getById(id);
    }

    
    @PostMapping
    public NewsEntity create(@RequestBody NewsEntity noticia) {
        return newsService.save(noticia);
    }

    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        newsService.delete(id);
    }
}
