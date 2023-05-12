package com.abel.tokoonline.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SignupRequest implements Serializable {
    private String username;
    private String password;
    private String nama;
    private String email;
}
