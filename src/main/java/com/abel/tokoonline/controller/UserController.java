package com.abel.tokoonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abel.tokoonline.dto.UserRespone;
import com.abel.tokoonline.model.User;
import com.abel.tokoonline.service.UserService;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/penggunas")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/penggunas/{id}")
    public User findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }

    @PostMapping("/penggunas")
    public UserRespone create(@RequestBody User pengguna) {
        return userService.create(pengguna);
    }

    @PutMapping("/penggunas")
    public User edit(@RequestBody User pengguna) {
        return userService.edit(pengguna);
    }

    @DeleteMapping("/penggunas/{id}")
    public void delete(@PathVariable("id") String id) {
        userService.deleteById(id);
    }

}
