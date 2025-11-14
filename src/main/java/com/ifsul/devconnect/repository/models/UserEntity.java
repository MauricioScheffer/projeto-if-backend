package com.ifsul.devconnect.repository.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;


@Data
@Builder
@Entity
@Table(name = "tbl_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;



    public UserEntity() {} 

    public UserEntity(Integer id, String nome, String email, String senha, Tipo tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }


}