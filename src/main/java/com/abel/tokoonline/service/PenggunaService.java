package com.abel.tokoonline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abel.tokoonline.dto.PenggunaRespone;
import com.abel.tokoonline.model.Pengguna;
import com.abel.tokoonline.repository.PenggunaRepo;
import com.abel.tokoonline.validationExeption.BadRequestExeption;
import com.abel.tokoonline.validationExeption.ResourceNotFoundExeption;

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
    public PenggunaRespone create(Pengguna pengguna) {
        PenggunaRespone respone = new PenggunaRespone();
        if (penggunaRepo.existsById(pengguna.getId())) {
            respone.setMessage("username " + pengguna.getId() + " sudah terdaftar");
            respone.setStatus(400);
            return respone;

        }
        if (penggunaRepo.existsByEmail(pengguna.getEmail())) {
            respone.setMessage("email " + pengguna.getEmail() + " sudahterdaftar");
            respone.setStatus(400);
            return respone;
        }
        if (!StringUtils.hasText(pengguna.getNama())) {
            respone.setMessage("nama tidak boleh kosong");
            respone.setStatus(400);
        }

        penggunaRepo.save(pengguna);
        respone.setMessage("Success Create User");
        respone.setStatus(200);
        return respone;
    }

    public Pengguna edit(Pengguna pengguna) {
        if (!StringUtils.hasText(pengguna.getId())) {
            throw new BadRequestExeption("Usernama tidak boleh kosong");
        }
        if (!StringUtils.hasText(pengguna.getEmail())) {
            throw new BadRequestExeption("Email tidak boleh kosong");
        }
        return penggunaRepo.save(pengguna);
    }

    // untuk menghapus pengguna
    public void deleteById(String id) {
        penggunaRepo.deleteById(id);
    }
}
