package com.abel.tokoonline.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtRespone implements Serializable {
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;

    public JwtRespone(String token, String username, String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }

}
