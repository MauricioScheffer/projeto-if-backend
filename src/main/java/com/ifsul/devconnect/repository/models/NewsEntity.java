package com.ifsul.devconnect.repository.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Data
@Entity
@Table(name = "news")
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    private String titulo;

    @Column(length = 300) 
    private String resumo;

    @Column(columnDefinition = "TEXT") // >>> texto longo para o conteúdo completo
    private String conteudo;

    private String data;

    private String link;

    //@ManyToOne revisar
    //@JoinColumn(name = "id_projeto")
    //private Projeto projeto;
    
}
