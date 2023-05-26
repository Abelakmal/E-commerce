package com.abel.tokoonline.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupRequest implements Serializable {
    @NotBlank(message = "username tidak bisa kosong")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,}$", message = "username harus lebih dari 6 dengan kombinasi angka dan huruf")
    private String username;
    @NotBlank(message = "password tidak boleh kosong")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "password harus lebih dari 8 dengan kombinasi angka dan huruf")
    private String password;
    @NotBlank(message = "nama tidak boleh kosong")
    private String nama;
    @NotBlank(message = "email tidak boleh kosong")
    @Email(message = "format email salah")
    private String email;
}
