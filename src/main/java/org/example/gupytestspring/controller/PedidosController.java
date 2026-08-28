package org.example.gupytestspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gupytestspring.dto.PedidosRequestDTO;
import org.example.gupytestspring.dto.PedidosResponseDTO;
import org.example.gupytestspring.model.Pedidos;
import org.example.gupytestspring.service.PedidosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pedidos", description = "CRUD de pedidos")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/pedidos")
@RequiredArgsConstructor
public class PedidosController {

    private final PedidosService pedidosService;

    @Operation(summary = "Lista todos os pedidos")
    @GetMapping
    public List<PedidosResponseDTO> list() {
        return pedidosService.list().stream()
                .map(PedidosResponseDTO::fromEntity)
                .toList();
    }

    @Operation(summary = "Busca um pedido pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<PedidosResponseDTO> findById(@PathVariable Long id) {
        Pedidos pedido = pedidosService.findById(id);
        return ResponseEntity.ok(PedidosResponseDTO.fromEntity(pedido));
    }

    @Operation(summary = "Cria um novo pedido")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidosResponseDTO create(@Valid @RequestBody PedidosRequestDTO dto) {
        Pedidos pedido = pedidosService.create(dto);
        return PedidosResponseDTO.fromEntity(pedido);
    }

    @Operation(summary = "Atualiza um pedido existente")
    @PutMapping("/{id}")
    public ResponseEntity<PedidosResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PedidosRequestDTO dto) {
        Pedidos updated = pedidosService.update(id, dto);
        return ResponseEntity.ok(PedidosResponseDTO.fromEntity(updated));
    }

    @Operation(summary = "Remove um pedido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pedidosService.delete(id);
        return ResponseEntity.noContent().build();
    }
}