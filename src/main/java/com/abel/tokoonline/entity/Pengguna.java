package com.abel.tokoonline.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Pengguna implements Serializable {
    @Id
    private String id;
    private String nama;
    private String alamat;
    private String nomorHp;

}
