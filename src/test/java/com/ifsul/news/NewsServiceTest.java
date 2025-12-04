package com.ifsul.news;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.ifsul.devconnect.config.exception_handler.DomainException;
import com.ifsul.devconnect.repository.models.NewsEntity;
import com.ifsul.devconnect.repository.repositories.NewsRepository;
import com.ifsul.devconnect.services.featureNews.NewsServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsServiceImplTest {

    private NewsRepository newsRepository;
    private NewsServiceImpl newsService;

    @BeforeEach
    void setUp() {
        newsRepository = mock(NewsRepository.class);
        newsService = new NewsServiceImpl(newsRepository);    }

    @Test
    void shouldReturnAllNews() {
        NewsEntity news1 = NewsEntity.builder().id(1).titulo("News 1").build();
        NewsEntity news2 = NewsEntity.builder().id(2).titulo("News 2").build();

        when(newsRepository.findAll()).thenReturn(Arrays.asList(news1, news2));

        List<NewsEntity> result = newsService.getAll();

        assertEquals(2, result.size());
        verify(newsRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnNewsById() {
        NewsEntity news = NewsEntity.builder().id(1).titulo("News Title").build();

        when(newsRepository.findById(1)).thenReturn(Optional.of(news));

        NewsEntity result = newsService.getById(1);

        assertNotNull(result);
        assertEquals("News Title", result.getTitulo());
        verify(newsRepository, times(1)).findById(1);
    }

    @Test
    void shouldThrowExceptionWhenNewsNotFound() {
        int id = 99;

        when(newsRepository.findById(id)).thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, () -> newsService.getById(id));

        assertEquals("Notícia não encontrada", exception.getTitle());
        assertEquals("A notícia com o ID informado não foi encontrada.", exception.getDescription());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
 
    void shouldSaveNews() {
        NewsEntity news = NewsEntity.builder()
            .titulo("New News")
            .resumo("Resumo válido")
            .conteudo("Conteúdo válido")
            .data("19/10/2025") // formato correto
            .build();

        when(newsRepository.save(news)).thenReturn(news);

        NewsEntity result = newsService.save(news);

        assertEquals("New News", result.getTitulo());
        verify(newsRepository, times(1)).save(news);
    }


    @Test
    void shouldDeleteNewsById() {
        int id = 1;
        when(newsRepository.existsById(id)).thenReturn(true);
        doNothing().when(newsRepository).deleteById(id);

        newsService.delete(id);

        verify(newsRepository, times(1)).deleteById(id);
    }

    
@Test
void shouldThrowExceptionWhenSavingNewsWithEmptyTitle() {
    NewsEntity news = NewsEntity.builder()
        .titulo("") // título vazio
        .resumo("Resumo válido")
        .conteudo("Conteúdo válido")
        .build();

    DomainException exception = assertThrows(DomainException.class, () -> newsService.save(news));

    assertEquals("Título inválido", exception.getTitle());
    assertEquals("O título da notícia não pode estar vazio.", exception.getDescription());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
}


@Test
void shouldThrowExceptionWhenResumoIsTooLong() {
    String longResumo = "a".repeat(301); // 301 caracteres

    NewsEntity news = NewsEntity.builder()
        .titulo("Título válido")
        .resumo(longResumo)
        .conteudo("Conteúdo válido")
        .build();

    DomainException exception = assertThrows(DomainException.class, () -> newsService.save(news));

    assertEquals("Resumo muito longo", exception.getTitle());
    assertEquals("O resumo da notícia não pode ultrapassar 300 caracteres.", exception.getDescription());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
}


@Test
void shouldThrowExceptionWhenConteudoIsNull() {
    NewsEntity news = NewsEntity.builder()
        .titulo("Título válido")
        .resumo("Resumo válido")
        .conteudo(null)
        .build();

    DomainException exception = assertThrows(DomainException.class, () -> newsService.save(news));

    assertEquals("Conteúdo inválido", exception.getTitle());
    assertEquals("O conteúdo da notícia não pode ser nulo ou vazio.", exception.getDescription());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

    }
    
@Test
void shouldThrowExceptionWhenDataIsInvalid() {
    NewsEntity news = NewsEntity.builder()
        .titulo("Título válido")
        .resumo("Resumo válido")
        .conteudo("Conteúdo válido")
        .data("2025-10-19") // formato errado
        .build();

    DomainException exception = assertThrows(DomainException.class, () -> newsService.save(news));

    assertEquals("Data inválida", exception.getTitle());
    assertEquals("A data da notícia deve estar no formato dd/MM/yyyy.", exception.getDescription());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
}


@Test
void shouldThrowExceptionWhenDeletingNonexistentNews() {
    int id = 99;

    when(newsRepository.existsById(id)).thenReturn(false);

    DomainException exception = assertThrows(DomainException.class, () -> newsService.delete(id));

    assertEquals("Notícia não encontrada", exception.getTitle());
    assertEquals("Não foi possível deletar: notícia com o ID informado não existe.", exception.getDescription());
  


    }
}