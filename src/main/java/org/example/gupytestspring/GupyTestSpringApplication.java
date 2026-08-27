package org.example.gupytestspring;

import org.example.gupytestspring.model.Pedidos;
import org.example.gupytestspring.model.UserModel;
import org.example.gupytestspring.repository.PedidosRepository;
import org.example.gupytestspring.repository.UserRepository;
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
            pedido.setPrice(14);
            pedido.setDescription("hello");
            pedidosRepository.save(pedido);
        };
    }

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository){
        return args -> {
            userRepository.deleteAll();
            UserModel user = new UserModel();
            user.setName("Alexsander");
            user.setEmail("acvb.dev@gmail.com");
            user.setCel(12345678);
            user.setPassword("123456");
            userRepository.save(user);
        };
    }



}

