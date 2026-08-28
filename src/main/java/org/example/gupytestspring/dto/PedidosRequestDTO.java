package org.example.gupytestspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PedidosRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 200, message = "O nome deve ter no máximo 200 caracteres")
        String name,

        @NotBlank(message = "A categoria é obrigatória")
        @Size(max = 20, message = "A categoria deve ter no máximo 20 caracteres")
        String category,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        Integer price,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String description
) {
}