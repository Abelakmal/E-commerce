package com.abel.tokoonline.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Pengguna {

    private String id;
    private String nama;
    private String alamat;
    private String nomorHp;

}
