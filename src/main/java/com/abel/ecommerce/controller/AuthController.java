package com.abel.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abel.ecommerce.dto.JwtRespone;
import com.abel.ecommerce.dto.RefreshTokenRequest;
import com.abel.ecommerce.dto.ResponeHttp;
import com.abel.ecommerce.dto.SigninRequest;
import com.abel.ecommerce.dto.SignupRequest;
import com.abel.ecommerce.model.Users;
import com.abel.ecommerce.security.jjwt.JwtUtils;
import com.abel.ecommerce.security.service.UserDetailImpl;
import com.abel.ecommerce.security.service.UserDetailServiceImpl;
import com.abel.ecommerce.service.AuthService;
import com.abel.ecommerce.service.UserService;

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
    AuthService authService;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;

    @PostMapping("/signin")
    public ResponseEntity<ResponeHttp> signin(@Valid @RequestBody SigninRequest request) {

        ResponeHttp result = authService.signin(request);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponeHttp> signup(@Valid @RequestBody SignupRequest request) {
        Users user = new Users();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRoles("user");

        ResponeHttp result = userService.create(user);

        return ResponseEntity.status(HttpStatus.valueOf(result.getStatus())).body(result);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtRespone> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        boolean valid = jwtUtils.validateJwtToken(refreshToken);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String email = jwtUtils.getEmailFromJwtToken(refreshToken);
        UserDetailImpl userDetailImpl = (UserDetailImpl) userDetailServiceImpl.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailImpl, null,
                userDetailImpl.getAuthorities());
        String newToken = jwtUtils.generateJwtToken(authentication);
        String newRefreshToken = jwtUtils.generateRefreshJwtToken(authentication);
        return ResponseEntity.ok().body(
                new JwtRespone(newToken, newRefreshToken, userDetailImpl.getEmail()));
    }
}
