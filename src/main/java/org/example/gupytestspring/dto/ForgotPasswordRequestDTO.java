package org.example.gupytestspring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ForgotPasswordRequestDTO(

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O e-mail informado é inválido")
        String email,

        @NotBlank(message = "A nova senha é obrigatória")
        @Size(min = 6, max = 255, message = "A senha deve ter entre 6 e 255 caracteres")
        String newPassword
) {
}