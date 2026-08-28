package org.example.gupytestspring.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column (length = 255, nullable = false)
    private String name;

    @Column (unique=true, length = 255, nullable = false)
    private String email;

    @Column (unique = true, length = 255, nullable = false)
    private String cel;

    @Column (length = 255, nullable = false)
    private String password;
}
