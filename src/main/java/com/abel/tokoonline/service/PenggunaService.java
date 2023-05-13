package com.abel.tokoonline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abel.tokoonline.entity.Pengguna;
import com.abel.tokoonline.exeption.BadRequestExeption;
import com.abel.tokoonline.exeption.ResourceNotFoundExeption;
import com.abel.tokoonline.repository.PenggunaRepo;

@Service
public class PenggunaService {

    @Autowired
    private PenggunaRepo penggunaRepo;

    // untuk menemukan user dengan id
    public Pengguna findById(String id) {
        return penggunaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Tidak ditemukan pengguna dengan id : " + id));
    }

    // untuk menampilkan semua pengguna yg ada didalam database
    public List<Pengguna> findAll() {
        return penggunaRepo.findAll();
    }

    // untuk membuat pengguna baru
    public Pengguna create(Pengguna pengguna) {
        exitsOrNotEmpty(pengguna);
        return penggunaRepo.save(pengguna);
    }

    // untuk menghapus pengguna
    public void deleteById(String id) {
        penggunaRepo.deleteById(id);
    }

    public void exitsOrNotEmpty(Pengguna pengguna) {
        if (!StringUtils.hasText(pengguna.getId())) {
            throw new BadRequestExeption("username tidak boleh kosong");
        }
        if (penggunaRepo.existsById(pengguna.getId())) {
            throw new BadRequestExeption("username " + pengguna.getId() + " sudah terdaftar");
        }
        if (!StringUtils.hasText(pengguna.getEmail())) {
            throw new BadRequestExeption("email harus diisi");
        }
        if (penggunaRepo.existsByEmail(pengguna.getEmail())) {
            throw new BadRequestExeption("email " + pengguna.getEmail() + " sudahterdaftar");
        }
        if (!StringUtils.hasText(pengguna.getPassword())) {
            throw new BadRequestExeption("password tidak boleh kosong");
        }
        if (!StringUtils.hasText(pengguna.getNama())) {
            throw new BadRequestExeption("nama tidak boleh kosong");
        }
        // cek data tidak boleh kosong

    }
}
