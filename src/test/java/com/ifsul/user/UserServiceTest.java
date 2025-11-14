
package com.ifsul.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ifsul.devconnect.routes.dto.user.UserCreateDTO;
import com.ifsul.devconnect.routes.dto.user.UserDTO;
import com.ifsul.devconnect.repository.models.Tipo;
import com.ifsul.devconnect.repository.models.UserEntity;
import com.ifsul.devconnect.repository.repositories.UserRepository;
import com.ifsul.devconnect.services.User.UserServiceImpl;
import com.ifsul.devconnect.config.exception_handler.DomainException;

import java.util.List;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
        when(encoder.encode(anyString())).thenReturn("senhaCriptografada");

        userService = new UserServiceImpl(userRepository, encoder);
    }

    @Test
    public void testSaveUserValido() {
        UserCreateDTO userDTO = new UserCreateDTO();
        userDTO.setNome("Rafael");
        userDTO.setEmail("rafael@teste.com");
        userDTO.setSenha("123456");
        userDTO.setTipo(Tipo.ALUNO);

        UserEntity savedEntity = UserEntity.builder()
            .id(1)
            .nome("Rafael")
            .email("rafael@teste.com")
            .senha("senhaCriptografada")
            .tipo(Tipo.ALUNO)
            .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedEntity);

        UserDTO result = userService.save(userDTO);
        assertEquals("Rafael", result.getNome());
        assertEquals("rafael@teste.com", result.getEmail());
    }

    @Test
    public void testSaveWithoutName() {
        UserCreateDTO userDTO = new UserCreateDTO();
        userDTO.setEmail("rafael@teste.com");
        userDTO.setSenha("123456");
        userDTO.setTipo(Tipo.ALUNO);

        DomainException ex = assertThrows(DomainException.class, () -> userService.save(userDTO));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        assertEquals("Nome inválido", ex.getTitle());
    }

    @Test
    public void testGetById_UserExists() {
        UserEntity user = UserEntity.builder()
            .id(1)
            .nome("Rafael")
            .email("rafael@teste.com")
            .senha("123456")
            .tipo(Tipo.ALUNO)
            .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDTO result = userService.getById(1);
        assertEquals(1, result.getId());
        assertEquals("Rafael", result.getNome());
    }

    @Test
    public void testDeleteUser_NonExistent() {
        when(userRepository.existsById(99)).thenReturn(false);

        DomainException ex = assertThrows(DomainException.class, () -> userService.delete(99));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("Usuário não encontrado", ex.getTitle());
    }

    @Test
    public void testFindByEmail_UserExists() {
        UserEntity user = UserEntity.builder()
            .id(1)
            .nome("Rafael")
            .email("rafael@teste.com")
            .senha("123456")
            .tipo(Tipo.ALUNO)
            .build();

        when(userRepository.findByEmail("rafael@teste.com")).thenReturn(Optional.of(user));

        Optional<UserEntity> result = userRepository.findByEmail("rafael@teste.com");
        assertTrue(result.isPresent());
        assertEquals("Rafael", result.get().getNome());
    }

    
    @Test // Ismael
        public void testGetAllUsers() {
            List<UserEntity> mockUsers = List.of(
                UserEntity.builder().id(1).nome("Ismael").email("ismael@gmail.com").tipo(Tipo.ALUNO).build(),
                UserEntity.builder().id(2).nome("Rodrigo").email("remor@gmail.com").tipo(Tipo.PROFESSOR).build()
            );

            when(userRepository.findAll()).thenReturn(mockUsers);

            List<UserDTO> result = userService.getAll();
            assertEquals(2, result.size());
            assertEquals("Ismael", result.get(0).getNome());
        }

        @Test
        public void testDeleteUser_Exists() {
            when(userRepository.existsById(1)).thenReturn(true);

            userService.delete(1);

            verify(userRepository).deleteById(1);
        }

        @Test
        public void testFindByTipo_Professor() {
            List<UserEntity> mockProfessores = List.of(
                UserEntity.builder().id(1).nome("Rodrigo").email("rodrigo@gmail.com").tipo(Tipo.PROFESSOR).build()
            );

            when(userRepository.findByTipo(Tipo.PROFESSOR)).thenReturn(mockProfessores);

            List<UserDTO> result = userService.findByTipo(Tipo.PROFESSOR);
            assertEquals(1, result.size());
            assertEquals("Rodrigo", result.get(0).getNome());
            assertEquals(Tipo.PROFESSOR, result.get(0).getTipo());
        }
}



