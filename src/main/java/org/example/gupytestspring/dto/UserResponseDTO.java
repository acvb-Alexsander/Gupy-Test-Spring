package org.example.gupytestspring.dto;

import org.example.gupytestspring.model.UserModel;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String cel
) {
    public static UserResponseDTO fromEntity(UserModel user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCel()
        );
    }
}