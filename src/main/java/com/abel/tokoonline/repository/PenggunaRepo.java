package com.abel.tokoonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.tokoonline.entity.Pengguna;

//untuk melakukan CRUD yang sudah ditangani otomatis oleh JPA
public interface PenggunaRepo extends JpaRepository<Pengguna, String> {

}
