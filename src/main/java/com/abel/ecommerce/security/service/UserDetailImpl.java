package com.abel.ecommerce.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.abel.ecommerce.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDetailImpl implements UserDetails {

    private int id;
    @JsonIgnore
    private String password;
    private String email;
    private String name;
    private String roles;

    public UserDetailImpl(int id, String password, String email, String name, String roles) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.roles = roles;
    }

    public static UserDetailImpl build(Users user) {
        return new UserDetailImpl(
                user.getId(),
                user.getPassword(),
                user.getEmail(),
                user.getName(),
                user.getRoles());
    }

    @Override
    public String getUsername() {
        return this.email;
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