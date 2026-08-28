package org.example.gupytestspring.controller;

import lombok.AllArgsConstructor;
import org.example.gupytestspring.model.Pedidos;
import org.example.gupytestspring.model.UserModel;
import org.example.gupytestspring.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<?> create(@RequestBody UserModel user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("E-mail já cadastrado");
        }

        UserModel savedUser = userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> update(@PathVariable Long id, @RequestBody UserModel user){
        return userRepository.findById(id).map(recordFound -> {
            recordFound.setName(user.getName());
            recordFound.setEmail(user.getEmail());
            recordFound.setCel(user.getCel());
            recordFound.setPassword(user.getPassword());
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

    @PostMapping("/login")
    public ResponseEntity<UserModel> login(@RequestBody UserModel user) {

        return userRepository
                .findByEmailAndPassword(
                        user.getEmail(),
                        user.getPassword()
                )
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .build()
                );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody UserModel user
    ) {

        Optional<UserModel> userOptional =
                userRepository.findByEmail(user.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("E-mail não encontrado");
        }

        UserModel userFound = userOptional.get();

        userFound.setPassword(user.getPassword());

        userRepository.save(userFound);

        return ResponseEntity.ok("Senha alterada com sucesso");
    }
}
