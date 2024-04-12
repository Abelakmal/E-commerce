package com.abel.ecommerce.service;

import org.springframework.stereotype.Service;

import com.abel.ecommerce.dto.JwtRespone;
import com.abel.ecommerce.dto.ResponeHttp;
import com.abel.ecommerce.dto.SigninRequest;
import com.abel.ecommerce.model.Users;
import com.abel.ecommerce.repository.UserRepo;
import com.abel.ecommerce.security.jjwt.JwtUtils;
import com.abel.ecommerce.security.service.UserDetailImpl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepo userRepo;

    private ResponeHttp respone = new ResponeHttp();

    public ResponeHttp signin(SigninRequest request) {

        Optional<Users> user = userRepo.findByEmail(request.getEmail());

        if (!user.isPresent()) {
            respone.setMessage("Email is not Found");
            respone.setStatus(404);
            return respone;
        }

        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            respone.setMessage("Password is wrong");
            respone.setStatus(400);
            return respone;
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        String refreshtToken = jwtUtils.generateRefreshJwtToken(authentication);
        UserDetailImpl principal = (UserDetailImpl) authentication.getPrincipal();

        respone.setMessage("Success Login");
        respone.setStatus(200);
        respone.setData(new JwtRespone(token, refreshtToken, principal.getEmail()));
        
        return respone;
    }
}
