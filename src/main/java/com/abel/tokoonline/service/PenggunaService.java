package com.abel.tokoonline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abel.tokoonline.entity.Pengguna;
import com.abel.tokoonline.exveption.ResourceNotFoundExeption;
import com.abel.tokoonline.repository.PenggunaRepo;

@Service
public class PenggunaService {

    @Autowired
    PenggunaRepo penggunaRepo;

    public Pengguna findById(String id) {
        return penggunaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Tidak ditemukan pengguna dengan id : " + id));
    }

    public List<Pengguna> findAll() {
        return penggunaRepo.findAll();
    }

    public Pengguna create(Pengguna pengguna) {
        return penggunaRepo.save(pengguna);
    }

    public void deleteById(String id) {
        penggunaRepo.deleteById(id);
    }
}
