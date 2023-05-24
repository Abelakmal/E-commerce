package com.abel.tokoonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.tokoonline.entity.Produk;

public interface ProdukRepo extends JpaRepository<Produk, String> {

}
