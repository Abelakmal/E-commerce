package com.abel.tokoonline.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abel.tokoonline.model.Users;
import com.abel.tokoonline.repository.UserRepo;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("username " + username + " tidak ditemukan"));
        return UserDetailImpl.build(user);
    }

}
