package com.ifsul.devconnect.repository.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;
}