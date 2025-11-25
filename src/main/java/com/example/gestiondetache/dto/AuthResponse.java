package com.example.gestiondetache.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;

    public AuthResponse(String token, String id, String username, String email) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
