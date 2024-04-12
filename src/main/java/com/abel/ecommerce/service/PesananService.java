package com.abel.ecommerce.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abel.ecommerce.model.Keranjang;
import com.abel.ecommerce.model.Users;
import com.abel.ecommerce.model.Pesanan;
import com.abel.ecommerce.model.PesananItem;
import com.abel.ecommerce.model.Produk;
import com.abel.ecommerce.validationExeption.BadRequestExeption;
import com.abel.ecommerce.validationExeption.ResourceNotFoundExeption;
import com.abel.ecommerce.dto.KeranjangRequest;
import com.abel.ecommerce.dto.PesananRequest;
import com.abel.ecommerce.dto.PesananRespon;
import com.abel.ecommerce.model.StatusPesanan;
import com.abel.ecommerce.repository.KeranjangRepo;
import com.abel.ecommerce.repository.PesananItemRepo;
import com.abel.ecommerce.repository.PesananRepo;
import com.abel.ecommerce.repository.ProdukRepo;

@Service
public class PesananService {

    @Autowired
    private ProdukRepo produkRepo;
    @Autowired
    private PesananRepo pesananaRepo;
    @Autowired
    private PesananItemRepo pesananItemRepo;
    @Autowired
    private KeranjangService keranjangService;
    @Autowired
    private KeranjangRepo keranjangRepo;
    @Autowired
    private PesananLogService pesananLogService;

    // membuat pesanan
    public PesananRespon create(int id, PesananRequest request) {
        Pesanan pesanan = new Pesanan();
        pesanan.setId(UUID.randomUUID().toString());
        pesanan.setTanggal(new Date());
        pesanan.setNomor(generateNomorPesanan());
        pesanan.setPengguna(new Users(id));
        pesanan.setStatusPesanan(StatusPesanan.DRAFT);
        pesanan.setWaktuPesanan(new Date());

        // cek produk apa ada atau tidak dan stock mencukupi atau tidak
        List<PesananItem> items = new ArrayList<>();
        for (KeranjangRequest k : request.getItems()) {
            Produk produk = produkRepo.findById(k.getProdukId())
                    .orElseThrow(() -> new BadRequestExeption("Produk ID " + k.getProdukId() + " tidak ditemukan"));
            if (produk.getStok() < k.getKuantitas()) {
                throw new BadRequestExeption("Stok tidak ditemukan");
            }
            // masukan ke pesananItem
            PesananItem pi = new PesananItem();
            pi.setId(UUID.randomUUID().toString());
            pi.setProduk(produk);
            pi.setDeskripsi(produk.getNama());
            pi.setKuantitas(k.getKuantitas());
            pi.setHarga(produk.getHarga());
            pi.setJumlah(new BigDecimal(pi.getHarga().doubleValue() * pi.getKuantitas()));
            pi.setPesanan(pesanan);
            items.add(pi);
        }
        // hitung jumlah pesanan di pesananItem
        BigDecimal jumlah = BigDecimal.ZERO;
        for (PesananItem pesananItem : items) {
            jumlah = jumlah.add(pesananItem.getJumlah());
        }
        // masukan ke tabel pesanan
        pesanan.setJumlah(jumlah);
        pesanan.setOngkir(request.getOngkir());
        pesanan.setTotal(pesanan.getJumlah().add(pesanan.getOngkir()));

        // save pesanan hapus pesanan produk dari keranjang
        Pesanan saved = pesananaRepo.save(pesanan);
        for (PesananItem pesananItem : items) {
            pesananItemRepo.save(pesananItem);
            Produk produk = pesananItem.getProduk();
            produk.setStok(produk.getStok() - pesananItem.getKuantitas());
            produkRepo.save(produk);

            Keranjang keranjang = keranjangRepo.findByUserIdAndProdukId(id, produk.getId())
                    .orElseThrow(() -> new ResourceNotFoundExeption("tidak ditemukan"));
            if (keranjang.getKuantitas() - pesananItem.getKuantitas() == 0) {
                keranjangService.delete(id, produk.getId());
            } else {
                keranjangService.updateKuantitas(id, produk.getId(),
                        keranjang.getKuantitas() - pesananItem.getKuantitas());
            }
        }

        // catat log
        pesananLogService.createLog(id, pesanan, PesananLogService.DRAFT, "Pesanan sukses dibuat");
        PesananRespon pesananRespon = new PesananRespon(saved, items);
        return pesananRespon;
    }

    // generate nomor
    private String generateNomorPesanan() {
        return String.format("%016d", System.nanoTime());
    }

    // cancel pesanan yg hanya bisa oleh pengguna
    @Transactional
    public Pesanan cancelPesanan(String pesananId, int userId) {
        Pesanan pesanan = pesananaRepo.findById(pesananId)
                .orElseThrow(() -> new ResourceNotFoundExeption("Pesanan ID " + pesananId + " tidak ditemukan"));

        if (userId != pesanan.getPengguna().getId()) {
            throw new BadRequestExeption("Pesanan ini hanya dapat dibatalkan oleh yang bersangkutan");
        }
        if (!StatusPesanan.DRAFT.equals(pesanan.getStatusPesanan())) {
            throw new BadRequestExeption("Pesanan tidak dapat dibatalkan karena sudah diproses ");
        }

        pesanan.setStatusPesanan(StatusPesanan.DIBATALKAN);
        Pesanan saved = pesananaRepo.save(pesanan);
        pesananLogService.createLog(userId, saved, PesananLogService.DIBATALKAN, "Pesanan sukses dibatalkan");
        return saved;
    }

    // pesanan saat pelanggan menerima pesanan
    @Transactional
    public Pesanan terimaPesanan(String pesananId, int userId) {
        Pesanan pesanan = pesananaRepo.findById(pesananId)
                .orElseThrow(() -> new ResourceNotFoundExeption("Pesanan ID " + pesananId + " tidak ditemukan"));

        if (userId != pesanan.getPengguna().getId()) {
            throw new BadRequestExeption("Pesanan ini hanya dapat dipatalkan oleh yang bersangkutan");
        }
        if (!StatusPesanan.PENGIRIMAN.equals(pesanan.getStatusPesanan())) {
            throw new BadRequestExeption(
                    "Penerimaan gagal, status pesanan saat ini adalah " + pesanan.getStatusPesanan().name());
        }

        pesanan.setStatusPesanan(StatusPesanan.DIBATALKAN);
        Pesanan saved = pesananaRepo.save(pesanan);
        pesananLogService.createLog(userId, saved, PesananLogService.DIBATALKAN, "Pesanan sukses dibatalkan");
        return saved;
    }

    // untuk menemukan all pesanan user
    public List<Pesanan> findAllPesananUser(int userId, int page, int limit) {
        return pesananaRepo.findByUserId(userId,
                PageRequest.of(page, limit, Sort.by("waktuPesanan").descending()));
    }

    public List<Pesanan> search(String filterText, int page, int limit) {
        return pesananaRepo.search(filterText, PageRequest.of(page, limit, Sort.by("waktuPesanan").descending()));
    }

    // konfirmasiPembayaran
    @Transactional
    public Pesanan konfirmasiPembayaran(String pesananId, int userId) {
        Pesanan pesanan = pesananaRepo.findById(pesananId)
                .orElseThrow(() -> new ResourceNotFoundExeption("Pesanan ID " + pesananId + " tidak ditemukan"));

        if (!StatusPesanan.DRAFT.equals(pesanan.getStatusPesanan())) {
            throw new BadRequestExeption(
                    "Konfirmasi pesanan gagal, status pesanan saat ini adalah " + pesanan.getStatusPesanan().name());
        }

        pesanan.setStatusPesanan(StatusPesanan.PEMBAYARAN);
        Pesanan saved = pesananaRepo.save(pesanan);
        pesananLogService.createLog(
                userId,
                saved,
                PesananLogService.PEMBAYARAN, "Pembayaran sukses dikonfirmasi");
        return saved;
    }

    // packing
    @Transactional
    public Pesanan packing(String pesananId, int userId) {
        Pesanan pesanan = pesananaRepo.findById(pesananId)
                .orElseThrow(() -> new ResourceNotFoundExeption("Pesanan ID " + pesananId + " tidak ditemukan"));

        if (!StatusPesanan.PEMBAYARAN.equals(pesanan.getStatusPesanan())) {
            throw new BadRequestExeption(
                    "Packing pesanan gagal, status pesanan saat ini adalah " + pesanan.getStatusPesanan().name());
        }

        pesanan.setStatusPesanan(StatusPesanan.PACKING);
        Pesanan saved = pesananaRepo.save(pesanan);
        pesananLogService.createLog(userId, saved, PesananLogService.PACKING, "Pesanan sedang disiapkan");
        return saved;
    }

    @Transactional
    public Pesanan kirim(String pesananId, int userId) {
        Pesanan pesanan = pesananaRepo.findById(pesananId)
                .orElseThrow(() -> new ResourceNotFoundExeption("Pesanan ID " + pesananId + " tidak ditemukan"));

        if (!StatusPesanan.PACKING.equals(pesanan.getStatusPesanan())) {
            throw new BadRequestExeption(
                    "Pengiriman pesanan gagal, status pesanan saat ini adalah " + pesanan.getStatusPesanan().name());
        }

        pesanan.setStatusPesanan(StatusPesanan.PENGIRIMAN);
        Pesanan saved = pesananaRepo.save(pesanan);
        pesananLogService.createLog(userId, saved, PesananLogService.PACKING, "Pesanan sedang dikirim");
        return saved;
    }

}
