
package com.ifsul.devconnect.services.User;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ifsul.devconnect.config.exception_handler.DomainException;
import com.ifsul.devconnect.repository.models.Tipo;
import com.ifsul.devconnect.repository.models.UserEntity;
import com.ifsul.devconnect.repository.repositories.UserRepository;
import com.ifsul.devconnect.routes.dto.user.UserCreateDTO;
import com.ifsul.devconnect.routes.dto.user.UserDTO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(int id) {
        UserEntity entity = userRepository.findById(id)
            .orElseThrow(() -> new DomainException("Usuário não encontrado", "O usuário com o ID informado não foi encontrado.", HttpStatus.NOT_FOUND));
        return toDTO(entity);
    }

    @Override
    public UserDTO save(UserCreateDTO user) {
        if (user.getNome() == null || user.getNome().trim().isEmpty()) {
            throw new DomainException("Nome inválido", "O nome do usuário não pode estar vazio.", HttpStatus.BAD_REQUEST);
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new DomainException("Email inválido", "O email do usuário deve ser válido.", HttpStatus.BAD_REQUEST);
        }
        if (user.getSenha() == null || user.getSenha().length() < 6) {
            throw new DomainException("Senha fraca", "A senha deve conter pelo menos 6 caracteres.", HttpStatus.BAD_REQUEST);
        }

        UserEntity entity = toEntity(user);
        UserEntity savedEntity = userRepository.save(entity);
        return toDTO(savedEntity);
    }

    @Override
    public void delete(int id) {
        if (!userRepository.existsById(id)) {
            throw new DomainException("Usuário não encontrado", "Não foi possível deletar: usuário com o ID informado não existe.", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(UserEntity entity) {
        return UserDTO.builder()
            .id(entity.getId())
            .nome(entity.getNome())
            .email(entity.getEmail())
            .tipo(entity.getTipo())
            .build();
    }

    private UserEntity toEntity(UserCreateDTO dto) {
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
        return UserEntity.builder()
            .nome(dto.getNome())
            .email(dto.getEmail())
            .senha(senhaCriptografada)
            .tipo(dto.getTipo())
            .build();
    }

    @Override
    public List<UserDTO> findByTipo(Tipo tipo) {
        return userRepository.findByTipo(tipo)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findProfessores() {
        return userRepository.findByTipo(Tipo.PROFESSOR)
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
