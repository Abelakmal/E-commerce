package com.abel.tokoonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.tokoonline.entity.Pengguna;

public interface PenggunaRepo extends JpaRepository<Pengguna, String> {

}
