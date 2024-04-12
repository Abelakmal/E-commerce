package com.abel.ecommerce.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abel.ecommerce.model.Users;
import com.abel.ecommerce.repository.UserRepo;

import jakarta.validation.ValidationException;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Users> user = userRepo.findByEmail(email);

        if (user.isEmpty()) {
            throw new ValidationException("Email is not found");
        }

        return UserDetailImpl.build(user.get());
    }

}
