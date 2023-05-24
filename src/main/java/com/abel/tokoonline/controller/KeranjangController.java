package com.abel.tokoonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abel.tokoonline.entity.Keranjang;
import com.abel.tokoonline.model.KeranjangRequest;
import com.abel.tokoonline.security.service.UserDetailImpl;
import com.abel.tokoonline.service.KeranjangService;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class KeranjangController {

    @Autowired
    KeranjangService keranjangService;

    @GetMapping("/keranjangs")
    public List<Keranjang> findByPenggunaId(@AuthenticationPrincipal UserDetailImpl user) {
        return keranjangService.findByPenggunaId(user.getUsername());
    }

    @PostMapping("/keranjangs")
    public Keranjang create(@AuthenticationPrincipal UserDetailImpl user, @RequestBody KeranjangRequest request) {
        return keranjangService.addKeranjang(user.getUsername(), request.getProdukId(), request.getKuantitas());
    }

    @PatchMapping("/keranjangs/{produkId}")
    public Keranjang update(@AuthenticationPrincipal UserDetailImpl user, @PathVariable("produkId") String produkId,
            @RequestParam("kuantitas") Double kuantitas) {
        return keranjangService.ubahKuantitas(user.getUsername(), produkId, kuantitas);
    }

    @DeleteMapping("/keranjangs/{produkId}")
    public void delete(@AuthenticationPrincipal UserDetailImpl user, @PathVariable("produkId") String produkId) {
        keranjangService.delete(user.getUsername(), produkId);
    }
}
