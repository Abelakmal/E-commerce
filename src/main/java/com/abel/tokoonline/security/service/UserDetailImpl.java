package com.abel.tokoonline.security.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.abel.tokoonline.entity.Pengguna;

import lombok.Data;

@Data
public class UserDetailImpl implements UserDetails {

    private String username;
    private String password;
    private String email;
    private String nama;

    public UserDetailImpl(String username, String password, String email, String nama) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nama = nama;
    }

    public static UserDetailImpl build(Pengguna pengguna) {
        return new UserDetailImpl(
                pengguna.getId(),
                pengguna.getPassword(),
                pengguna.getEmail(),
                pengguna.getNama());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
