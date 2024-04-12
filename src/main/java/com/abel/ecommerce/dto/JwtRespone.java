package com.abel.ecommerce.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtRespone implements Serializable {
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private String email;

    public JwtRespone(String token, String refreshToken, String email) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.email = email;
    }

}
