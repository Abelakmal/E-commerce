package com.abel.tokoonline.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.abel.tokoonline.entity.Pengguna;
import com.abel.tokoonline.repository.PenggunaRepo;

public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    PenggunaRepo penggunaRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Pengguna pengguna = penggunaRepo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("username " + username + " tidak ditemukan"));
        return UserDetailImpl.build(pengguna);
    }

}
