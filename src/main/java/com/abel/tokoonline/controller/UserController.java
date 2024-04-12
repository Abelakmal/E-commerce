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
import com.abel.tokoonline.model.Users;
import com.abel.tokoonline.service.UserService;

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
    public Users findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }

    @PostMapping("/penggunas")
    public UserRespone create(@RequestBody Users user) {
        return userService.create(user);
    }

    @PutMapping("/penggunas")
    public Users edit(@RequestBody Users user) {
        return userService.edit(user);
    }

    @DeleteMapping("/penggunas/{id}")
    public void delete(@PathVariable("id") String id) {
        userService.deleteById(id);
    }

}
