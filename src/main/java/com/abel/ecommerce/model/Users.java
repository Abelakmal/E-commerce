package com.abel.ecommerce.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Users implements Serializable {
    // data pengguna
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;
    private String name;

    @JsonIgnore
    private String password;
    // @JsonIgnore
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String address;
    @JsonIgnore
    @Column(unique = true)
    private String numberPhone;
    // @JsonIgnore
    private String roles;

    public Users(int id) {
        this.id = id;
    }
}
