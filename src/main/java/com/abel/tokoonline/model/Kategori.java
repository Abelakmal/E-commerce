package com.abel.tokoonline.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Kategori implements Serializable {

    @Id
    private String id;
    private String nama;
}
