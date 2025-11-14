package com.ifsul.devconnect.routes.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.ifsul.devconnect.repository.models.Tipo;


@Getter
@Setter
@Builder

public class UserDTO {
    private int id;
    private String nome;
    private String email;
    private Tipo tipo;
}
