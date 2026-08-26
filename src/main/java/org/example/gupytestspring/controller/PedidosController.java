package org.example.gupytestspring.controller;

import lombok.AllArgsConstructor;
import org.example.gupytestspring.model.Pedidos;
import org.example.gupytestspring.repository.PedidosRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/pedidos")
@AllArgsConstructor
public class PedidosController {

    private final PedidosRepository pedidosRepository;

    @GetMapping
    public List<Pedidos> list(){
        return pedidosRepository.findAll();
    }
}
