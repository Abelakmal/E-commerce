package com.abel.tokoonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abel.tokoonline.entity.Pengguna;
import com.abel.tokoonline.model.SignupRequest;

@RestController("/auth")
public class AuthController {

    @Autowired

    @PostMapping("/signup")
    public Pengguna signup(@RequestBody SignupRequest request) {
        Pengguna pengguna = new Pengguna();
        pengguna.setId(request.getUsername());
        pengguna.setPassword(Encode);

    }
}
