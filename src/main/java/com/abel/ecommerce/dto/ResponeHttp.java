package com.abel.ecommerce.dto;


import lombok.Data;

@Data
public class ResponeHttp {
    private String message;
    private int status;
    private Object data;
}
