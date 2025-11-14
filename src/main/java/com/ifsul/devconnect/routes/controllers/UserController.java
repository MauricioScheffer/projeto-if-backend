package com.ifsul.devconnect.routes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.ifsul.devconnect.repository.models.Tipo;
import com.ifsul.devconnect.repository.models.UserEntity;
import com.ifsul.devconnect.routes.dto.user.UserCreateDTO;
import com.ifsul.devconnect.routes.dto.user.UserDTO;
import com.ifsul.devconnect.services.User.UserService;
import com.ifsul.devconnect.services.User.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

  
    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }


    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable int id) {
        return userService.getById(id);
    }

    @PostMapping
    public UserDTO create(@RequestBody UserCreateDTO user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }

    
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<UserDTO>> listarPorTipo(@PathVariable Tipo tipo) {
        return ResponseEntity.ok(userService.findByTipo(tipo));
    }

    @GetMapping("/professores")
    public ResponseEntity<List<UserDTO>> listarProfessores() {
        return ResponseEntity.ok(userService.findProfessores());
    }


}