package com.abel.tokoonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abel.tokoonline.entity.Pengguna;
import com.abel.tokoonline.service.PenggunaService;

@RestController
@RequestMapping("/api")
public class PenggunaController {

    @Autowired
    PenggunaService penggunaService;

    @GetMapping("/pengguna")
    public List<Pengguna> findAll() {
        return penggunaService.findAll();
    }

    @GetMapping("/pengguna/{id}")
    public Pengguna findById(@PathVariable("id") String id) {
        return penggunaService.findById(id);
    }

    @PostMapping("/pengguna")
    public Pengguna create(@RequestBody Pengguna pengguna) {

        return penggunaService.create(pengguna);
    }

    @DeleteMapping("/pengguna/{id}")
    public void delete(@PathVariable("id") String id) {
        penggunaService.deleteById(id);
    }

}
