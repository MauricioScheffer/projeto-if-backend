package com.ifsul.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.ifsul.devconnect.repository.models.UserEntity;
import com.ifsul.devconnect.repository.models.Tipo;
import com.ifsul.devconnect.repository.repositories.UserRepository;
import com.ifsul.devconnect.services.featureUsuario.UserServiceImpl;
import com.ifsul.devconnect.config.exception_handler.DomainException;

public class UserService {
    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
        when(encoder.encode(anyString())).thenReturn("senhaCriptografada");

        userService = new UserServiceImpl(userRepository, encoder, null);
    }

    @Test
    public void testSaveUserValido() {
        UserEntity user = UserEntity.builder()
                .name("Rafael")
                .email("rafael@teste.com")
                .password("123456")
                .type(Tipo.ALUNO)
                .build();

        when(userRepository.save(user)).thenReturn(user);

        UserEntity result = userService.save(user);
        assertEquals("Rafael", result.getName());
    }

    @Test
    public void testSaveWhithoutName() {
        UserEntity user = UserEntity.builder()
                .email("rafael@teste.com")
                .password("123456")
                .type(Tipo.ALUNO)
                .build();

        DomainException ex = assertThrows(DomainException.class, () -> userService.save(user));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    public void testGetById_UserExists() {
        UserEntity user = UserEntity.builder()
                .id(1)
                .name("Rafael")
                .email("rafael@teste.com")
                .password("123456")
                .type(Tipo.ALUNO)
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserEntity result = userService.getById(1);
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteUser_NonExistent() {
        when(userRepository.existsById(99)).thenReturn(false);

        DomainException ex = assertThrows(DomainException.class, () -> userService.delete(99));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    public void testFindByEmail_UserExists() {
        UserEntity user = UserEntity.builder()
                .name("Rafael")
                .email("rafael@teste.com")
                .password("123456")
                .type(Tipo.ALUNO)
                .build();

        when(userRepository.findByEmail("rafael@teste.com")).thenReturn(Optional.of(user));

        Optional<UserEntity> result = userRepository.findByEmail("rafael@teste.com");
        assertTrue(result.isPresent());
        assertEquals("Rafael", result.get().getName());
    }

}
