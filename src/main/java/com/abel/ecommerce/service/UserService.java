package com.abel.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abel.ecommerce.dto.ResponeHttp;
import com.abel.ecommerce.model.Users;
import com.abel.ecommerce.repository.UserRepo;
import com.abel.ecommerce.validationExeption.ResourceNotFoundExeption;

import jakarta.validation.ValidationException;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    private ResponeHttp respone = new ResponeHttp();

    // untuk menemukan user dengan id
    public Users findById(int id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Tidak ditemukan pengguna dengan id : " + id));
    }

    // untuk menampilkan semua pengguna yg ada didalam database
    public List<Users> findAll() {
        return userRepo.findAll();
    }

    // untuk membuat pengguna baru
    public ResponeHttp create(Users pengguna) {
        try {
            if (userRepo.existsByEmail(pengguna.getEmail())) {
                respone.setMessage("email " + pengguna.getEmail() + " sudah terdaftar");
                respone.setStatus(400);
                return respone;
            }
            if (!StringUtils.hasText(pengguna.getName())) {
                respone.setMessage("nama tidak boleh kosong");
                respone.setStatus(400);
                return respone;
            }
            System.out.println("testtsssssssssssssssseeeeeeeeeeeeeee");

            userRepo.save(pengguna);
            respone.setMessage("Success Create User");
            respone.setStatus(200);
            return respone;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }

    public ResponeHttp edit(Users user, int id) {
        // try {
        Optional<Users> isExist = userRepo.findById(id);
        if (!isExist.isPresent()) {
            respone.setMessage("id is not found");
            respone.setStatus(404);
            return respone;
        }

        Users update = isExist.get();
        update.setAddress(user.getAddress());
        update.setName(user.getName());
        update.setNumberPhone(user.getNumberPhone());

        userRepo.save(update);
        respone.setMessage("Success Update User");
        respone.setStatus(200);
        return respone;
        // } catch (Exception e) {
        // throw new ValidationException(e);
        // }
    }

    // untuk menghapus pengguna
    public ResponeHttp deleteById(int id) {
        try {
            Optional<Users> isExist = userRepo.findById(id);
            if (!isExist.isPresent()) {
                respone.setMessage("id is not found");
                respone.setStatus(404);
                return respone;
            }
            userRepo.deleteById(id);
            respone.setMessage("Success Delete");
            respone.setStatus(200);
            return respone;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
