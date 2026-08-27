package org.example.gupytestspring;

import org.example.gupytestspring.model.Pedidos;
import org.example.gupytestspring.repository.PedidosRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GupyTestSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(GupyTestSpringApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(PedidosRepository pedidosRepository){
        return args -> {
            pedidosRepository.deleteAll();
            Pedidos pedido = new Pedidos();
            pedido.setName("Angular com Spring");
            pedido.setCategory("front-end");
            pedidosRepository.save(pedido);
        };
    }


}

