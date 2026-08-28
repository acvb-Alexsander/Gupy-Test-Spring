package org.example.gupytestspring.service;

import lombok.RequiredArgsConstructor;
import org.example.gupytestspring.dto.ForgotPasswordRequestDTO;
import org.example.gupytestspring.dto.LoginRequestDTO;
import org.example.gupytestspring.dto.UserRequestDTO;
import org.example.gupytestspring.exception.EmailAlreadyExistsException;
import org.example.gupytestspring.exception.InvalidCredentialsException;
import org.example.gupytestspring.exception.ResourceNotFoundException;
import org.example.gupytestspring.model.UserModel;
import org.example.gupytestspring.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * NOTA: a senha é persistida e comparada em texto puro nesta versão do teste técnico.
 * Isso é uma limitação conhecida, documentada na nota técnica (NOTA_TECNICA.md),
 * e não uma omissão — em produção, seria usado um hash (ex.: BCrypt) e um mecanismo
 * de token de sessão real (ex.: JWT) no lugar do retorno direto da entidade.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserModel> list() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserModel findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: id=" + id));
    }

    @Transactional
    public UserModel create(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException("E-mail já cadastrado: " + dto.email());
        }
        UserModel user = new UserModel();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setCel(dto.cel());
        user.setPassword(dto.password());
        UserModel saved = userRepository.save(user);
        log.info("Usuário criado: id={}, email={}", saved.getId(), saved.getEmail());
        return saved;
    }

    @Transactional
    public UserModel update(Long id, UserRequestDTO dto) {
        UserModel user = findById(id);
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setCel(dto.cel());
        user.setPassword(dto.password());
        UserModel updated = userRepository.save(user);
        log.info("Usuário atualizado: id={}", updated.getId());
        return updated;
    }

    @Transactional
    public void delete(Long id) {
        UserModel user = findById(id);
        userRepository.delete(user);
        log.info("Usuário removido: id={}", id);
    }

    @Transactional(readOnly = true)
    public UserModel login(LoginRequestDTO dto) {
        UserModel user = userRepository.findByEmailAndPassword(dto.email(), dto.password())
                .orElseThrow(() -> new InvalidCredentialsException("E-mail ou senha inválidos"));
        log.info("Login bem-sucedido: email={}", user.getEmail());
        return user;
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequestDTO dto) {
        UserModel user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ResourceNotFoundException("E-mail não encontrado: " + dto.email()));
        user.setPassword(dto.newPassword());
        userRepository.save(user);
        log.info("Senha redefinida: email={}", user.getEmail());
    }
}