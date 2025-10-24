package com.ifsul.devconnect.services.featureUsuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.ifsul.devconnect.config.exception_handler.DomainException;
import com.ifsul.devconnect.repository.models.UserEntity;
import com.ifsul.devconnect.repository.repositories.UserRepository;
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
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity save(UserEntity user) {
    
        if (user.getNome() == null || user.getNome().trim().isEmpty()) {
            throw new DomainException("Nome inválido", "O nome do usuário não pode estar vazio.", HttpStatus.BAD_REQUEST);
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new DomainException("Email inválido", "O email do usuário deve ser válido.", HttpStatus.BAD_REQUEST);
        }
        if (user.getSenha() == null || user.getSenha().length() < 6) {
            throw new DomainException("Senha fraca", "A senha deve conter pelo menos 6 caracteres.", HttpStatus.BAD_REQUEST);
        }
   
        String senhaCriptografada = passwordEncoder.encode(user.getSenha());
        user.setSenha(senhaCriptografada);

        return userRepository.save(user);
    }

    @Override
    public UserEntity getById(int id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new DomainException("Usuário não encontrado", "O usuário com o ID informado não foi encontrado.", HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(int id) {
        if (!userRepository.existsById(id)) {
            throw new DomainException("Usuário não encontrado", "Não foi possível deletar: usuário com o ID informado não existe.", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }


}
