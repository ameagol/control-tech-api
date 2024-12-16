package com.api.control_tech.models;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String accessToken;

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
