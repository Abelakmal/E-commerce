package com.abel.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.ecommerce.model.Keranjang;

public interface KeranjangRepo extends JpaRepository<Keranjang, String> {

    Optional<Keranjang> findByUserIdAndProdukId(int id, String produkId);

    List<Keranjang> findByUserId(int id);

}
