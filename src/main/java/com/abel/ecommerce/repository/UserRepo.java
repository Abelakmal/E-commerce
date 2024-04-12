package com.abel.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.ecommerce.model.Users;


//untuk melakukan CRUD yang sudah ditangani otomatis oleh JPA
public interface UserRepo extends JpaRepository<Users, Integer> {

    boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);

}
