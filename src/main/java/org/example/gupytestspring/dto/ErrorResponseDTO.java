package org.example.gupytestspring.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponseDTO(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldErrorDTO> fields
) {
    public record FieldErrorDTO(String field, String message) {
    }

    public static ErrorResponseDTO of(int status, String error, String message, String path) {
        return new ErrorResponseDTO(Instant.now(), status, error, message, path, List.of());
    }

    public static ErrorResponseDTO of(int status, String error, String message, String path, List<FieldErrorDTO> fields) {
        return new ErrorResponseDTO(Instant.now(), status, error, message, path, fields);
    }
}