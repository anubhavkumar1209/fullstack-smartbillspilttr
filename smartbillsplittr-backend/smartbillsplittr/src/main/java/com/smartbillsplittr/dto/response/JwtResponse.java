package com.smartbillsplittr.dto.response;

public class JwtResponse {
    private String token;
    private String type = "Bearer";

    public JwtResponse(String accessToken, String bearer) {
        this.token = accessToken;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
