package com.abel.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.ecommerce.model.Kategori;

public interface KategoriRepo extends JpaRepository<Kategori, String> {

}
