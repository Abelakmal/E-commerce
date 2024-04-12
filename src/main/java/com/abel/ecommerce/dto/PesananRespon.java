package com.abel.ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abel.ecommerce.model.Pesanan;
import com.abel.ecommerce.model.PesananItem;

import lombok.Data;

@Data
public class PesananRespon {
    private String id;
    private String nomorPesanan;
    private Date tanggal;
    private String namaPelanggan;
    private String alamatpengiriman;
    private Date waktuPesan;
    private BigDecimal jumlah;
    private BigDecimal ongkir;
    private BigDecimal total;
    private List<PesananRespon.Item> items;

    public PesananRespon(Pesanan pesanan, List<PesananItem> pesananItems) {
        this.id = pesanan.getId();
        this.nomorPesanan = pesanan.getNomor();
        this.tanggal = pesanan.getTanggal();
        this.namaPelanggan = pesanan.getPengguna().getName();
        this.alamatpengiriman = pesanan.getAlamatPengiriman();
        this.waktuPesan = pesanan.getWaktuPesanan();
        this.jumlah = pesanan.getJumlah();
        this.ongkir = pesanan.getOngkir();
        this.total = pesanan.getTotal();
        items = new ArrayList<>();
        for (PesananItem pesananItem : pesananItems) {
            Item item = new Item();
            item.setProdukId(pesananItem.getProduk().getId());
            item.setNamaProduk(pesananItem.getDeskripsi());
            item.setKuantitas(pesananItem.getKuantitas());
            item.setHarga(pesananItem.getHarga());
            item.setJumlah(pesananItem.getJumlah());
            items.add(item);
        }
    }

    @Data
    public static class Item implements Serializable {
        private String produkId;
        private String namaProduk;
        private Double kuantitas;
        private BigDecimal harga;
        private BigDecimal jumlah;

    }
}
