package com.abel.ecommerce.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abel.ecommerce.model.Users;
import com.abel.ecommerce.model.Pesanan;
import com.abel.ecommerce.model.PesananLog;
import com.abel.ecommerce.repository.PesananLogRepo;

@Service
public class PesananLogService {
    @Autowired
    private PesananLogRepo pesananaLogRepo;

    public final static int DRAFT = 0;
    public final static int PEMBAYARAN = 0;
    public final static int PACKING = 0;
    public final static int PENGIRIMAN = 0;
    public final static int SELESAI = 0;
    public final static int DIBATALKAN = 0;

    public void createLog(int id, Pesanan pesanan, int type, String message) {
        PesananLog p = new PesananLog();
        p.setId(UUID.randomUUID().toString());
        p.setLogMessage(message);
        p.setLogType(type);
        p.setPengguna(new Users(id));
        p.setWaktuPesan(new Date());
        pesananaLogRepo.save(p);
    }
}
