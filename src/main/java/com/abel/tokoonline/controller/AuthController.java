package com.abel.tokoonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.abel.tokoonline.dto.UserRespone;
import com.abel.tokoonline.dto.RefreshTokenRequest;
import com.abel.tokoonline.dto.SigninRequest;
import com.abel.tokoonline.dto.SignupRequest;
import com.abel.tokoonline.model.Users;
import com.abel.tokoonline.security.jjwt.JwtUtils;
import com.abel.tokoonline.security.service.UserDetailImpl;
import com.abel.tokoonline.security.service.UserDetailServiceImpl;
import com.abel.tokoonline.service.UserService;

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
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;

    @PostMapping("/signin")
    public ResponseEntity<JwtRespone> signin(@Valid @RequestBody SigninRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        String refreshtToken = jwtUtils.generateRefreshJwtToken(authentication);
        UserDetailImpl principal = (UserDetailImpl) authentication.getPrincipal();

        return ResponseEntity.ok()
                .body(new JwtRespone(token, refreshtToken, principal.getUsername(), principal.getEmail()));
    }


    @PostMapping("/signup")
    public ResponseEntity<UserRespone> signup(@Valid @RequestBody SignupRequest request) {
        Users user = new Users();
        user.setId(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setName(request.getNama());
        user.setRoles("user");

        UserRespone result = userService.create(user);

        return ResponseEntity.status(HttpStatus.valueOf(result.getStatus())).body(result);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtRespone> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        boolean valid = jwtUtils.validateJwtToken(refreshToken);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        UserDetailImpl userDetailImpl = (UserDetailImpl) userDetailServiceImpl.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailImpl, null,
                userDetailImpl.getAuthorities());
        String newToken = jwtUtils.generateJwtToken(authentication);
        String newRefreshToken = jwtUtils.generateRefreshJwtToken(authentication);
        return ResponseEntity.ok().body(
                new JwtRespone(newToken, newRefreshToken, username, userDetailImpl.getEmail()));
    }
}
