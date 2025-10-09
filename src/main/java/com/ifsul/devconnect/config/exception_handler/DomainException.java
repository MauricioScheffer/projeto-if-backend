package com.ifsul.devconnect.config.exception_handler;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private String title;
    private String description;

    private HttpStatus status;

    public DomainException(String title, String description, HttpStatus status) {
        super(title);
        this.title = title;
        this.description = description;
        this.status = status;
    }

}
