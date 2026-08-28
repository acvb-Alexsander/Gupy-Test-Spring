package org.example.gupytestspring.controller;

import lombok.AllArgsConstructor;
import org.example.gupytestspring.model.Pedidos;
import org.example.gupytestspring.model.UserModel;
import org.example.gupytestspring.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Long id){
        return userRepository.findById(id).map(record-> ResponseEntity.ok().body(record)).
                orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserModel create(@RequestBody UserModel pedido){
        //System.out.println(Pedidos.getName());
        return userRepository.save(pedido); }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> update(@PathVariable Long id, @RequestBody UserModel pedido){
        return userRepository.findById(id).map(recordFound -> {
            recordFound.setName(pedido.getName());
            recordFound.setEmail(pedido.getEmail());
            recordFound.setCel(pedido.getCel());
            recordFound.setPassword(pedido.getPassword());
            UserModel updated = userRepository.save(recordFound);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return userRepository.findById(id).map(recordFound ->{
            userRepository.deleteById(id);
            return  ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
