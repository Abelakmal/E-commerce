package com.abel.ecommerce.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupRequest implements Serializable {
    @NotBlank(message = "password tidak boleh kosong")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "password harus lebih dari 8 dengan kombinasi angka dan huruf")
    private String password;
    @NotBlank(message = "nama tidak boleh kosong")
    private String name;
    @NotBlank(message = "email tidak boleh kosong")
    @Email(message = "format email salah")
    private String email;
}
