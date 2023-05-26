package com.abel.tokoonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.tokoonline.model.Produk;

public interface ProdukRepo extends JpaRepository<Produk, String> {

}
