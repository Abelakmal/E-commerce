package com.abel.tokoonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abel.tokoonline.dto.JwtRespone;
import com.abel.tokoonline.dto.SigninRequest;
import com.abel.tokoonline.dto.SignupRequest;
import com.abel.tokoonline.model.Pengguna;
import com.abel.tokoonline.security.jjwt.JwtUtils;
import com.abel.tokoonline.security.service.UserDetailImpl;
import com.abel.tokoonline.service.PenggunaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PenggunaService penggunaService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtRespone> signin(@Valid @RequestBody SigninRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        UserDetailImpl principal = (UserDetailImpl) authentication.getPrincipal();

        return ResponseEntity.ok()
                .body(new JwtRespone(token, principal.getUsername(), principal.getEmail()));
    }

    @PostMapping("/signup")
    public Pengguna signup(@Valid @RequestBody SignupRequest request) {
        Pengguna pengguna = new Pengguna();
        pengguna.setId(request.getUsername());
        pengguna.setPassword(passwordEncoder.encode(request.getPassword()));
        pengguna.setEmail(request.getEmail());
        pengguna.setNama(request.getNama());
        pengguna.setRoles("user");

        Pengguna created = penggunaService.create(pengguna);

        return created;
    }
}
