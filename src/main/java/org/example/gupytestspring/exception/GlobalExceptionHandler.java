package org.example.gupytestspring.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.example.gupytestspring.dto.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Tratamento centralizado de exceções da API.
 * Garante que toda resposta de erro siga um formato único e que cada erro
 * fique registrado no log com contexto suficiente para diagnóstico.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("Recurso não encontrado em [{} {}]: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.of(HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflict(EmailAlreadyExistsException ex, HttpServletRequest request) {
        log.warn("Conflito em [{} {}]: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDTO.of(HttpStatus.CONFLICT.value(), "Conflict", ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(InvalidCredentialsException ex, HttpServletRequest request) {
        log.warn("Falha de autenticação em [{} {}]: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseDTO.of(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.warn("Violação de integridade de dados em [{} {}]: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDTO.of(HttpStatus.CONFLICT.value(), "Conflict",
                        "Os dados enviados violam uma restrição de unicidade ou integridade.", request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErrorResponseDTO.FieldErrorDTO> fields = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ErrorResponseDTO.FieldErrorDTO(fe.getField(), fe.getDefaultMessage()))
                .toList();
        log.info("Payload inválido em [{} {}]: {}", request.getMethod(), request.getRequestURI(), fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.of(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                        "Um ou mais campos são inválidos.", request.getRequestURI(), fields));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Erro não tratado em [{} {}]", request.getMethod(), request.getRequestURI(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDTO.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                        "Ocorreu um erro inesperado. Consulte os logs do servidor para mais detalhes.", request.getRequestURI()));
    }
}