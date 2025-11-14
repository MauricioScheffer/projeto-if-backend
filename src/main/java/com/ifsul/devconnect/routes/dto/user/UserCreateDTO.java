package com.ifsul.devconnect.routes.dto.user;

import lombok.Getter;
import lombok.Setter;
import com.ifsul.devconnect.repository.models.Tipo;

@Getter
@Setter
public class UserCreateDTO {
    private String nome;
    private String email;
    private String senha;
    private Tipo tipo;    
}
