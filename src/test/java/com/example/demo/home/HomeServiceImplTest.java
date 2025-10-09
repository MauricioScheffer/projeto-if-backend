package com.example.demo.home;

import com.example.devconnect.config.exception_handler.DomainException;
import com.example.devconnect.repository.models.HomeEntity;
import com.example.devconnect.repository.repositories.IHomeRepository;
import com.example.devconnect.services.Home.HomeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class HomeServiceImplTest {

    private IHomeRepository homeRepository;
    private HomeServiceImpl homeService;

    @BeforeEach
    void setUp() {
        homeRepository = mock(IHomeRepository.class);
        homeService = new HomeServiceImpl(homeRepository);
    }

    @Test
    void shouldGetWelcomeMessage() {
        long id = 1L;
        HomeEntity mockEntity = HomeEntity.builder()
                .id(id)
                .welcomeMessage("Bem-vindo ao DevConnect!")
                .build();

        when(homeRepository.findById(id)).thenReturn(mockEntity);

        String message = homeService.getWelcomeMessage(id);

        assertEquals("Bem-vindo ao DevConnect!", message);
        verify(homeRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenMessageNotFound() {
        long id = 2L;

        when(homeRepository.findById(id)).thenReturn(null);

        DomainException exception = assertThrows(DomainException.class, () -> homeService.getWelcomeMessage(id));

        assertEquals("Mensagem não encontrada", exception.getTitle());
        assertEquals("A mensagem não foi encontrada para o id informado.", exception.getDescription());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}