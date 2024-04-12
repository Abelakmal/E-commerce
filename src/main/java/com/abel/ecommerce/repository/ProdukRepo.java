package com.abel.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.ecommerce.model.Produk;

public interface ProdukRepo extends JpaRepository<Produk, String> {

}
