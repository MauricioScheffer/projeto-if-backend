package com.example.devconnect.routes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.devconnect.routes.dto.home.HomeResponse;
import com.example.devconnect.services.Home.HomeService;

@RestController
@RequestMapping("/home")
public class HomeController {
    private HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/{id}")
    public HomeResponse home(@PathVariable long id) {
        String welcomeMessage = homeService.getWelcomeMessage(id);
        return HomeResponse.builder()
                .message(welcomeMessage)
                .build();
    }
}
