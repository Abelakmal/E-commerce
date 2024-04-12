package com.abel.ecommerce.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RefreshTokenRequest implements Serializable {
    private String refreshToken;
}
