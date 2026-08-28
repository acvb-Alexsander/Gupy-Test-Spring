package org.example.gupytestspring.service;

import lombok.RequiredArgsConstructor;
import org.example.gupytestspring.dto.PedidosRequestDTO;
import org.example.gupytestspring.exception.ResourceNotFoundException;
import org.example.gupytestspring.model.Pedidos;
import org.example.gupytestspring.repository.PedidosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidosService {

    private static final Logger log = LoggerFactory.getLogger(PedidosService.class);

    private final PedidosRepository pedidosRepository;

    @Transactional(readOnly = true)
    public List<Pedidos> list() {
        return pedidosRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedidos findById(Long id) {
        return pedidosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado: id=" + id));
    }

    @Transactional
    public Pedidos create(PedidosRequestDTO dto) {
        Pedidos pedido = new Pedidos();
        pedido.setName(dto.name());
        pedido.setCategory(dto.category());
        pedido.setPrice(dto.price());
        pedido.setDescription(dto.description());
        Pedidos saved = pedidosRepository.save(pedido);
        log.info("Pedido criado: id={}", saved.getId());
        return saved;
    }

    @Transactional
    public Pedidos update(Long id, PedidosRequestDTO dto) {
        Pedidos pedido = findById(id);
        pedido.setName(dto.name());
        pedido.setCategory(dto.category());
        pedido.setPrice(dto.price());
        pedido.setDescription(dto.description());
        Pedidos updated = pedidosRepository.save(pedido);
        log.info("Pedido atualizado: id={}", updated.getId());
        return updated;
    }

    @Transactional
    public void delete(Long id) {
        Pedidos pedido = findById(id);
        pedidosRepository.delete(pedido);
        log.info("Pedido removido: id={}", id);
    }
}