package com.abel.tokoonline.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class User implements Serializable {
    // data pengguna
    @Id
    private String id;
    private String name;

    @JsonIgnore
    private String password;
    // @JsonIgnore
    private String email;
    @JsonIgnore
    private String address;
    @JsonIgnore
    private String numberPhone;
    // @JsonIgnore
    private String roles;

    public User(String username) {
        this.id = username;
    }
}
