package com.abel.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abel.ecommerce.dto.ResponeHttp;
import com.abel.ecommerce.model.Users;
import com.abel.ecommerce.service.UserService;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/penggunas")
    public List<Users> findAll() {
        return userService.findAll();
    }

    @GetMapping("/penggunas/{id}")
    public Users findById(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @PostMapping("/penggunas")
    public ResponeHttp create(@RequestBody Users user) {
        return userService.create(user);
    }

    @PatchMapping("/penggunas/{id}")
    public ResponeHttp edit(@PathVariable("id") int id , @RequestBody Users user) {
        return userService.edit(user,id);
    }

    @DeleteMapping("/penggunas/{id}")
    public void delete(@PathVariable("id") int id) {
        userService.deleteById(id);
    }

}
