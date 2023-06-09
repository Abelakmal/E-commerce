package com.abel.tokoonline.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abel.tokoonline.model.Kategori;
import com.abel.tokoonline.repository.KategoriRepo;
import com.abel.tokoonline.validationExeption.ResourceNotFoundExeption;

@Service
public class KategoriService {
    @Autowired
    private KategoriRepo kategoriRepo;

    public Kategori findById(String id) {
        return kategoriRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("tidak ditemukan kategori dengan id: " + id));
    }

    public List<Kategori> findAll() {
        return kategoriRepo.findAll();
    }

    public Kategori create(Kategori kategori) {
        kategori.setId(UUID.randomUUID().toString());
        return kategoriRepo.save(kategori);
    }

    public void deleteById(String id) {
        kategoriRepo.deleteById(id);
    }

    public Kategori edit(Kategori kategori) {
        return kategoriRepo.save(kategori);
    }
}
