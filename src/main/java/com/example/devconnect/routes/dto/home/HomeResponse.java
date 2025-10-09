package com.example.devconnect.routes.dto.home;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HomeResponse {
    private String message;

    public HomeResponse(String message) {
        this.message = message;
    }

}
