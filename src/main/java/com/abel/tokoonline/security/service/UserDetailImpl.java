package com.abel.tokoonline.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.abel.tokoonline.entity.Pengguna;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDetailImpl implements UserDetails {

    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String nama;
    private String roles;

    public UserDetailImpl(String username, String password, String email, String nama, String roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nama = nama;
        this.nama = roles;
    }

    public static UserDetailImpl build(Pengguna pengguna) {
        return new UserDetailImpl(
                pengguna.getId(),
                pengguna.getPassword(),
                pengguna.getEmail(),
                pengguna.getNama(),
                pengguna.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (StringUtils.hasText(roles)) {
            String[] splits = roles.replaceAll(" ", "").split(",");
            for (String s : splits) {
                authorities.add(new SimpleGrantedAuthority(s));
            }
        }
        return authorities;
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
