package com.cis.springboot.security;

public class JwtResponse {
    private String Token;

    public JwtResponse(String token) {
        Token = token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
