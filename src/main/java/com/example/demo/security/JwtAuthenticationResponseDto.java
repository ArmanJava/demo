package com.example.demo.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponseDto {

    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
