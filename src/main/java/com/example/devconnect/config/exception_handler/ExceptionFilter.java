package com.example.devconnect.config.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionFilter {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex, WebRequest request) {
        if (ex instanceof DomainException domainException) {
            ApiError error = new ApiError(domainException.getStatus().value(), domainException.getTitle(),
                    domainException.getDescription());

            return new ResponseEntity<>(error, domainException.getStatus());
        }

        ApiError error = new ApiError();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
