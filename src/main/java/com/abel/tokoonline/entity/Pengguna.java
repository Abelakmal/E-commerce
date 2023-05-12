package com.abel.tokoonline.entity;

import java.io.Serializable;

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
    private String password;
    private String email;
    private String alamat;
    private String nomorHp;

    public Pengguna(String username) {
        this.id = username;
    }
}
