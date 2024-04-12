package com.abel.tokoonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.tokoonline.model.User;

//untuk melakukan CRUD yang sudah ditangani otomatis oleh JPA
public interface UserRepo extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

}
