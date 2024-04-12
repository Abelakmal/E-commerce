package com.abel.tokoonline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abel.tokoonline.dto.UserRespone;
import com.abel.tokoonline.model.User;
import com.abel.tokoonline.repository.UserRepo;
import com.abel.tokoonline.validationExeption.BadRequestExeption;
import com.abel.tokoonline.validationExeption.ResourceNotFoundExeption;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    // untuk menemukan user dengan id
    public User findById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Tidak ditemukan pengguna dengan id : " + id));
    }

    // untuk menampilkan semua pengguna yg ada didalam database
    public List<User> findAll() {
        return userRepo.findAll();
    }

    // untuk membuat pengguna baru
    public UserRespone create(User pengguna) {
        UserRespone respone = new UserRespone();
        if (userRepo.existsById(pengguna.getId())) {
            respone.setMessage("username " + pengguna.getId() + " sudah terdaftar");
            respone.setStatus(400);
            return respone;

        }
        if (userRepo.existsByEmail(pengguna.getEmail())) {
            respone.setMessage("email " + pengguna.getEmail() + " sudahterdaftar");
            respone.setStatus(400);
            return respone;
        }
        if (!StringUtils.hasText(pengguna.getName())) {
            respone.setMessage("nama tidak boleh kosong");
            respone.setStatus(400);
        }

        userRepo.save(pengguna);
        respone.setMessage("Success Create User");
        respone.setStatus(200);
        return respone;
    }

    public User edit(User pengguna) {
        if (!StringUtils.hasText(pengguna.getId())) {
            throw new BadRequestExeption("Usernama tidak boleh kosong");
        }
        if (!StringUtils.hasText(pengguna.getEmail())) {
            throw new BadRequestExeption("Email tidak boleh kosong");
        }
        return userRepo.save(pengguna);
    }

    // untuk menghapus pengguna
    public void deleteById(String id) {
        userRepo.deleteById(id);
    }
}
