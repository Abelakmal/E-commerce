package com.abel.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.ecommerce.model.PesananItem;

public interface PesananItemRepo extends JpaRepository<PesananItem, String> {

}
