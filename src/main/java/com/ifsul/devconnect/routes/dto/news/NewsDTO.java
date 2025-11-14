package com.ifsul.devconnect.routes.dto.news;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class NewsDTO {
   
    private int id;
    private String titulo;
    private String resumo;
    private String conteudo;
    private String data;
    private String link;
    public NewsDTO(int id, String titulo, String resumo, String conteudo, String data, String link) {
        this.id = id;
        this.titulo = titulo;
        this.resumo = resumo;
        this.conteudo = conteudo;
        this.data = data;
        this.link = link;
    }
    
}
