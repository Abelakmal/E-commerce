package com.abel.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Keranjang implements Serializable {
    @Id
    private String id;
    @JoinColumn
    @ManyToOne
    private Produk produk;
    @JoinColumn
    @ManyToOne
    private Users user;
    private Double kuantitas;
    private BigDecimal harga;
    private BigDecimal jumlah;
}
