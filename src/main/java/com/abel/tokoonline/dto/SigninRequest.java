package com.abel.tokoonline.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SigninRequest implements Serializable {
    @NotBlank(message = "username tidak boleh kosong")
    private String username;
    @NotBlank(message = "password tidak boleh kosong")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "password harus lebih dari 8 dengan kombinasi angka dan huruf")
    private String password;
}
