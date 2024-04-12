package com.abel.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abel.ecommerce.model.Pesanan;
import com.abel.ecommerce.dto.PesananRequest;
import com.abel.ecommerce.dto.PesananRespon;
import com.abel.ecommerce.security.service.UserDetailImpl;
import com.abel.ecommerce.service.PesananService;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class PesananController {

    @Autowired
    private PesananService pesananService;

    @PostMapping("/pesanans")
    @PreAuthorize("hasAuthority('user')")
    public PesananRespon create(
            @AuthenticationPrincipal UserDetailImpl user,
            @RequestBody PesananRequest request) {

        return pesananService.create(user.getId(), request);
    }

    @PatchMapping("/pesanans/{pesananId}/cancel")
    @PreAuthorize("hasAuthority('user')")
    public Pesanan cancelPesanan(
            @AuthenticationPrincipal UserDetailImpl user,
            @PathVariable("pesananId") String pesananId) {
        return pesananService.cancelPesanan(pesananId, user.getId());
    }

    @PatchMapping("/pesanans/{pesananId}/terima")
    @PreAuthorize("hasAuthority('user')")
    public Pesanan terima(
            @AuthenticationPrincipal UserDetailImpl user,
            @PathVariable("pesananId") String pesananId) {
        return pesananService.terimaPesanan(pesananId, user.getId());
    }

    @PatchMapping("/pesanans/{pesananId}/konfirmasi")
    @PreAuthorize("hasAuthority('admin')")
    public Pesanan konfirmasi(
            @AuthenticationPrincipal UserDetailImpl user,
            @PathVariable("pesananId") String pesananId) {
        return pesananService.konfirmasiPembayaran(pesananId, user.getId());
    }

    @PatchMapping("/pesanans/{pesananId}/packing")
    @PreAuthorize("hasAuthority('admin')")
    public Pesanan packing(
            @AuthenticationPrincipal UserDetailImpl user,
            @PathVariable("pesananId") String pesananId) {
        return pesananService.packing(pesananId, user.getId());
    }

    @PatchMapping("/pesanans/{pesananId}/kirim")
    @PreAuthorize("hasAuthority('admin')")
    public Pesanan kirim(
            @AuthenticationPrincipal UserDetailImpl user,
            @PathVariable("pesananId") String pesananId) {
        return pesananService.kirim(pesananId, user.getId());
    }

    @GetMapping("/pesanans")
    @PreAuthorize("hasAuthority('user')")
    public List<Pesanan> findAllPesananUser(
            @AuthenticationPrincipal UserDetailImpl user,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {
        return pesananService.findAllPesananUser(user.getId(), page, limit);
    }

    @GetMapping("/pesanans/admin")
    @PreAuthorize("hasAuthority('admin')")
    public List<Pesanan> search(
            @AuthenticationPrincipal UserDetailImpl user,
            @RequestParam(name = "filterText", defaultValue = "", required = false) String filterText,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {
        return pesananService.search(filterText, page, limit);
    }
}