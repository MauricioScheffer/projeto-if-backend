package com.ifsul.devconnect.routes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.ifsul.devconnect.repository.models.UserEntity;
import com.ifsul.devconnect.routes.dto.user.LoginRequest;
import com.ifsul.devconnect.routes.dto.user.TokenResponse;
import com.ifsul.devconnect.services.featureUsuario.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest user) {
        return userService.login(user.getEmail(), user.getPassword());
    }

    @GetMapping
    public List<UserEntity> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserEntity getById(@PathVariable int id) {
        return userService.getById(id);
    }

    @PostMapping
    public UserEntity create(@RequestBody UserEntity user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }
}