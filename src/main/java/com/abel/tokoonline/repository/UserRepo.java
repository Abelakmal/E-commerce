package com.abel.tokoonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.tokoonline.model.Users;

//untuk melakukan CRUD yang sudah ditangani otomatis oleh JPA
public interface UserRepo extends JpaRepository<Users, String> {

    boolean existsByEmail(String email);

}
