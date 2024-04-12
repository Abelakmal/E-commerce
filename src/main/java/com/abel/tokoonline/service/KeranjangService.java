package com.abel.tokoonline.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abel.tokoonline.model.Keranjang;
import com.abel.tokoonline.model.User;
import com.abel.tokoonline.model.Produk;
import com.abel.tokoonline.repository.KeranjangRepo;
import com.abel.tokoonline.repository.ProdukRepo;
import com.abel.tokoonline.validationExeption.BadRequestExeption;
import com.abel.tokoonline.validationExeption.ResourceNotFoundExeption;

@Service
public class KeranjangService {

    @Autowired
    private ProdukRepo produkRepo;

    @Autowired
    private KeranjangRepo keranjangRepo;

    @Transactional
    public Keranjang addKeranjang(String username, String produkId, Double kuantitas) {
        // apakah produk ada di dalam database
        // apakah sudah ada dalam keranjang milik user
        // jika sudah ada maka update kuantitas nya dan hitung
        // jika belum ada maka buat baru
        Produk produk = produkRepo.findById(produkId)
                .orElseThrow(() -> new ResourceNotFoundExeption("Produk dengan Id: " + produkId + "tidak ditemukan"));
        Optional<Keranjang> optional = keranjangRepo.findByPenggunaIdAndProdukId(username, produkId);
        Keranjang keranjang;
        if (optional.isPresent()) {
            keranjang = new Keranjang();
            keranjang.setKuantitas(kuantitas);
            keranjang.setJumlah(new BigDecimal(keranjang.getHarga().doubleValue() * kuantitas));
            keranjangRepo.save(keranjang);
        } else {
            keranjang = new Keranjang();
            keranjang.setId(UUID.randomUUID().toString());
            keranjang.setProduk(produk);
            keranjang.setUser(new User(username));
            keranjang.setKuantitas(kuantitas);
            keranjang.setHarga(produk.getHarga());
            keranjang.setJumlah(new BigDecimal(produk.getHarga().doubleValue() + kuantitas));
            keranjangRepo.save(keranjang);
        }
        return keranjang;
    }

    @Transactional
    public Keranjang ubahKuantitas(String user, String produkId, Double kuantitas) {
        Keranjang keranjang = keranjangRepo.findByPenggunaIdAndProdukId(user, produkId)
                .orElseThrow(() -> new BadRequestExeption("tidak ditemukan id yang dicari"));
        keranjang.setKuantitas(kuantitas);
        keranjang.setJumlah(new BigDecimal(keranjang.getHarga().doubleValue() * keranjang.getKuantitas()));
        keranjangRepo.save(keranjang);
        return keranjang;
    }

    @Transactional
    public void delete(String username, String produkId) {
        Keranjang keranjang = keranjangRepo.findByPenggunaIdAndProdukId(username, produkId)
                .orElseThrow(() -> new BadRequestExeption("tidak ditemukan username dan produk id yang dicari"));

        keranjangRepo.delete(keranjang);
    }

    @Transactional
    public List<Keranjang> findByPenggunaId(String username) {
        return keranjangRepo.findByPenggunaId(username);
    }
}
