package com.example.devconnect.services.Home;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.devconnect.config.exception_handler.DomainException;
import com.example.devconnect.repository.models.HomeEntity;
import com.example.devconnect.repository.repositories.IHomeRepository;

@Service
public class HomeServiceImpl implements HomeService {
    private final IHomeRepository homeRepository;

    public HomeServiceImpl(IHomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    @Override
    public String getWelcomeMessage(long id) {
        HomeEntity homeEntity = homeRepository.findById(id);
        if (homeEntity == null) {
            throw new DomainException("Mensagem não encontrada", "A mensagem não foi encontrada para o id informado.",
                    HttpStatus.NOT_FOUND);
        }
        return homeEntity.getWelcomeMessage();
    }

}
