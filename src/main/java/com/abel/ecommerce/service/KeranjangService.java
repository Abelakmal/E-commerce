package com.abel.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abel.ecommerce.model.Keranjang;
import com.abel.ecommerce.model.Produk;
import com.abel.ecommerce.model.Users;
import com.abel.ecommerce.repository.KeranjangRepo;
import com.abel.ecommerce.repository.ProdukRepo;
import com.abel.ecommerce.validationExeption.BadRequestExeption;
import com.abel.ecommerce.validationExeption.ResourceNotFoundExeption;

@Service
public class KeranjangService {

    @Autowired
    private ProdukRepo produkRepo;

    @Autowired
    private KeranjangRepo keranjangRepo;

    @Transactional
    public Keranjang addKeranjang(int id, String produkId, Double kuantitas) {
        // apakah produk ada di dalam database
        // apakah sudah ada dalam keranjang milik user
        // jika sudah ada maka update kuantitas nya dan hitung
        // jika belum ada maka buat baru
        Produk produk = produkRepo.findById(produkId)
                .orElseThrow(() -> new ResourceNotFoundExeption("Produk dengan Id: " + produkId + "tidak ditemukan"));
        Optional<Keranjang> optional = keranjangRepo.findByUserIdAndProdukId(id, produkId);
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
            keranjang.setUser(new Users(id));
            keranjang.setKuantitas(kuantitas);
            keranjang.setHarga(produk.getHarga());
            keranjang.setJumlah(new BigDecimal(produk.getHarga().doubleValue() + kuantitas));
            keranjangRepo.save(keranjang);
        }
        return keranjang;
    }

    @Transactional
    public Keranjang updateKuantitas(int id, String produkId, Double kuantitas) {
        Keranjang keranjang = keranjangRepo.findByUserIdAndProdukId(id, produkId)
                .orElseThrow(() -> new BadRequestExeption("tidak ditemukan id yang dicari"));
        keranjang.setKuantitas(kuantitas);
        keranjang.setJumlah(new BigDecimal(keranjang.getHarga().doubleValue() * keranjang.getKuantitas()));
        keranjangRepo.save(keranjang);
        return keranjang;
    }

    @Transactional
    public void delete(int id, String produkId) {
        Keranjang keranjang = keranjangRepo.findByUserIdAndProdukId(id, produkId)
                .orElseThrow(() -> new BadRequestExeption("tidak ditemukan id dan produk id yang dicari"));

        keranjangRepo.delete(keranjang);
    }

    @Transactional
    public List<Keranjang> findByPenggunaId(int id) {
        return keranjangRepo.findByUserId(id);
    }
}
