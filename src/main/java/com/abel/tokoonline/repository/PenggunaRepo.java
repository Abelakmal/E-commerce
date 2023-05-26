package com.abel.tokoonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.tokoonline.model.Pengguna;

//untuk melakukan CRUD yang sudah ditangani otomatis oleh JPA
public interface PenggunaRepo extends JpaRepository<Pengguna, String> {

    boolean existsByEmail(String email);

}
