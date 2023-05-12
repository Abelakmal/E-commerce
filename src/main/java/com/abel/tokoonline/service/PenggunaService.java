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
    PenggunaRepo penggunaRepo;

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
        // if (!StringUtils.hasText(pengguna.getNama())) {
        // throw new BadRequestExeption("nama tidak boleh kosong");
        // }else if(!StringUtils.hasText(pengguna.getAlamat())){
        // throw new BadRequestExeption("alamat tidak boleh kosong");
        // }else if(!StringUtils.hasText(pengguna.getNomorHp())){
        // throw new BadRequestExeption("nomor hp tidak boleh kosong");
        // }
        return penggunaRepo.save(pengguna);
    }

    // untuk menghapus pengguna
    public void deleteById(String id) {
        penggunaRepo.deleteById(id);
    }
}
