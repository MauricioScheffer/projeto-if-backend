package com.ifsul.devconnect.services.featureUsuario;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.ifsul.devconnect.config.exception_handler.DomainException;
import com.ifsul.devconnect.config.token.GenerateToken;
import com.ifsul.devconnect.repository.models.TokenEntity;
import com.ifsul.devconnect.repository.models.UserEntity;
import com.ifsul.devconnect.repository.repositories.TokenRepository;
import com.ifsul.devconnect.repository.repositories.UserRepository;
import com.ifsul.devconnect.routes.dto.user.TokenResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
            TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity save(UserEntity user) {

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new DomainException("Nome inválido", "O nome do usuário não pode estar vazio.",
                    HttpStatus.BAD_REQUEST);
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new DomainException("Email inválido", "O email do usuário deve ser válido.", HttpStatus.BAD_REQUEST);
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new DomainException("Senha fraca", "A senha deve conter pelo menos 6 caracteres.",
                    HttpStatus.BAD_REQUEST);
        }

        String senhaCriptografada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCriptografada);

        return userRepository.save(user);
    }

    @Override
    public UserEntity getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new DomainException("Usuário não encontrado",
                        "O usuário com o ID informado não foi encontrado.", HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(int id) {
        if (!userRepository.existsById(id)) {
            throw new DomainException("Usuário não encontrado",
                    "Não foi possível deletar: usuário com o ID informado não existe.", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public TokenResponse login(String email, String senha) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new DomainException("Login falhou",
                    "Email ou senha inválidos.", HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = userOptional.get();

        if (!passwordEncoder.matches(senha, user.getPassword())) {
            throw new DomainException("Login falhou",
                    "Email ou senha inválidos.",
                    HttpStatus.UNAUTHORIZED);
        }

        TokenEntity tokenEntity = TokenEntity.builder()
                .expireAt(LocalDateTime.now().plusHours(3))
                .user(user)
                .build();

        UUID tokenId = tokenRepository.save(tokenEntity).getId();

        String token = GenerateToken.generate(tokenId.toString(),
                Date.from(LocalDateTime.now().plusHours(3).atZone(ZoneId.systemDefault()).toInstant()));

        return new TokenResponse(token);
    }

}
