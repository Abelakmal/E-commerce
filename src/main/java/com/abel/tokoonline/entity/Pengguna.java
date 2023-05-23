package com.abel.tokoonline.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Pengguna implements Serializable {
    // data pengguna
    @Id
    private String id;
    private String nama;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String alamat;
    @JsonIgnore
    private String nomorHp;
    @JsonIgnore
    private String roles;

    public Pengguna(String username) {
        this.id = username;
    }
}
