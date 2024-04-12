package com.abel.ecommerce.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abel.ecommerce.model.Produk;
import com.abel.ecommerce.repository.ProdukRepo;
import com.abel.ecommerce.validationExeption.ResourceNotFoundExeption;

@Service
public class ProdukService {
    @Autowired
    private ProdukRepo produkRepo;

    public Produk findById(String id) {
        return produkRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("tidak ditemukan produk dengan id: " + id));
    }

    public List<Produk> findAll() {
        return produkRepo.findAll();
    }

    public Produk create(Produk produk) {
        produk.setId(UUID.randomUUID().toString());
        return produkRepo.save(produk);
    }

    public Produk edit(Produk produk) {
        return produkRepo.save(produk);
    }

    public void deleteById(String id) {
        produkRepo.deleteById(id);
    }
}
