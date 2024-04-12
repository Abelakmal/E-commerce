package com.abel.ecommerce.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class KeranjangRequest implements Serializable {
    private String produkId;
    private Double kuantitas;
}
