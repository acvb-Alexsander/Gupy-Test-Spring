package org.example.gupytestspring.dto;

import org.example.gupytestspring.model.Pedidos;

public record PedidosResponseDTO(
        Long id,
        String name,
        String category,
        int price,
        String description
) {
    public static PedidosResponseDTO fromEntity(Pedidos pedido) {
        return new PedidosResponseDTO(
                pedido.getId(),
                pedido.getName(),
                pedido.getCategory(),
                pedido.getPrice(),
                pedido.getDescription()
        );
    }
}