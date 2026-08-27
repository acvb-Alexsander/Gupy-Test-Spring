package org.example.gupytestspring.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (length = 200, nullable = false)
    private String name;

    @Column (length = 20, nullable = false)
    private String category;

    @Column (length = 20, nullable = false)
    private int price;

    @Column (length = 255, nullable = false)
    private String description;



}
