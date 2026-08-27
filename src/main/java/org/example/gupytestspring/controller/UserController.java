package org.example.gupytestspring.controller;

import lombok.AllArgsConstructor;
import org.example.gupytestspring.model.Pedidos;
import org.example.gupytestspring.model.UserModel;
import org.example.gupytestspring.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor

public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public List<UserModel> list(){
        return userRepository.findAll();
    }
}
