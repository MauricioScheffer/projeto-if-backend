package com.example.devconnect.config.exception_handler;

import lombok.Getter;

@Getter
public class ApiError {
    private int status;
    private String title;
    private String description;

    public ApiError() {
        this(500, "Ocorreu um erro interno!",
                "Tente novamente mais tarde. Se o problema persistir, entre em contato com o suporte.");
    }

    public ApiError(int status, String title, String description) {
        this.status = status;
        this.title = title;
        this.description = description;
    }

}
