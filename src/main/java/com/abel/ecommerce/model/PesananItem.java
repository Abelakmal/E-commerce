package com.abel.ecommerce.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PesananItem {

    @Id
    private String id;
    @JoinColumn
    @ManyToOne
    private Pesanan pesanan;
    @JoinColumn
    @ManyToOne
    private Produk produk;
    private String deskripsi;
    private Double kuantitas;
    private BigDecimal harga;
    private BigDecimal jumlah;

}
