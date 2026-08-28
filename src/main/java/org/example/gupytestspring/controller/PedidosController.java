package org.example.gupytestspring.controller;

import lombok.AllArgsConstructor;
import org.example.gupytestspring.model.Pedidos;
import org.example.gupytestspring.repository.PedidosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/pedidos")
@AllArgsConstructor
public class PedidosController {

    private final PedidosRepository pedidosRepository;

    @GetMapping
    public List<Pedidos> list(){
        return pedidosRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedidos> findById(@PathVariable Long id){
        return pedidosRepository.findById(id).map(record-> ResponseEntity.ok().body(record)).
                orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pedidos create(@RequestBody Pedidos pedido){
        //System.out.println(Pedidos.getName());
        return pedidosRepository.save(pedido); }

    @PutMapping("/{id}")
    public ResponseEntity<Pedidos> update(@PathVariable Long id, @RequestBody Pedidos pedido){
        return pedidosRepository.findById(id).map(recordFound -> {
            recordFound.setName(pedido.getName());
            recordFound.setCategory(pedido.getCategory());
            recordFound.setPrice(pedido.getPrice());
            recordFound.setDescription(pedido.getDescription());
            Pedidos updated = pedidosRepository.save(recordFound);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return pedidosRepository.findById(id).map(recordFound ->{
            pedidosRepository.deleteById(id);
            return  ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
