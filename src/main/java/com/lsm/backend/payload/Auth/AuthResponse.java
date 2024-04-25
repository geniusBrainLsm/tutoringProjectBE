package com.lsm.backend.payload.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    // Getters and Setters (Omitted for brevity)
}