package org.example.gupytestspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gupytestspring.dto.ForgotPasswordRequestDTO;
import org.example.gupytestspring.dto.LoginRequestDTO;
import org.example.gupytestspring.dto.UserRequestDTO;
import org.example.gupytestspring.dto.UserResponseDTO;
import org.example.gupytestspring.model.UserModel;
import org.example.gupytestspring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "CRUD de usuários, login e recuperação de senha")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Lista todos os usuários")
    @GetMapping
    public List<UserResponseDTO> list() {
        return userService.list().stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
    }

    @Operation(summary = "Busca um usuário pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        UserModel user = userService.findById(id);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }

    @Operation(summary = "Cadastra um novo usuário")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@Valid @RequestBody UserRequestDTO dto) {
        UserModel user = userService.create(dto);
        return UserResponseDTO.fromEntity(user);
    }

    @Operation(summary = "Atualiza um usuário existente")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
        UserModel updated = userService.update(id, dto);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(updated));
    }

    @Operation(summary = "Remove um usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Autentica um usuário por e-mail e senha")
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        UserModel user = userService.login(dto);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }

    @Operation(summary = "Redefine a senha de um usuário a partir do e-mail")
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO dto) {
        userService.forgotPassword(dto);
        return ResponseEntity.ok().build();
    }
}