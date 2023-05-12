package com.abel.tokoonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abel.tokoonline.entity.Pengguna;
import com.abel.tokoonline.model.SignupRequest;
import com.abel.tokoonline.repository.PenggunaRepo;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PenggunaRepo penggunaRepo;

    @PostMapping("/signup")
    public Pengguna signup(@RequestBody SignupRequest request) {
        Pengguna pengguna = new Pengguna();
        pengguna.setId(request.getUsername());
        pengguna.setPassword(passwordEncoder.encode(request.getPassword()));
        pengguna.setEmail(request.getEmail());
        pengguna.setNama(request.getNama());

        Pengguna created = penggunaRepo.save(pengguna);

        return created;
    }
}
