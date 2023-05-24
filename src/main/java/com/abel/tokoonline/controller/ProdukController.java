package com.abel.tokoonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abel.tokoonline.entity.Produk;
import com.abel.tokoonline.service.ProdukService;

@RestController
@RequestMapping("/api")
public class ProdukController {
    @Autowired
    ProdukService produkService;

    @GetMapping("/produks/{id}")
    public Produk findById(@PathVariable String id) {
        return produkService.findById(id);
    }

    @GetMapping("/produks")
    public List<Produk> findAll() {
        return produkService.findAll();
    }

    @PostMapping("/produks")
    public Produk create(@RequestBody Produk produk) {
        return produkService.create(produk);
    }

    @PutMapping("/produks")
    public Produk edit(@RequestBody Produk produk) {
        return produkService.edit(produk);
    }

    @DeleteMapping("/produks/{id}")
    public void delete(@PathVariable("id") String id) {
        produkService.deleteById(id);
    }
}
