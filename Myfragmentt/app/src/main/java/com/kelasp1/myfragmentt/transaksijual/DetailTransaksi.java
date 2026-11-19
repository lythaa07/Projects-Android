package com.kelasp1.myfragmentt.transaksijual;

public class DetailTransaksi {
    private String kodetr, kode, nama, satuan;
    private int qty;
    private double harga, subtotal;

    public DetailTransaksi(String kodetr, String kode, String nama, String satuan, int qty, double harga, double subtotal) {
        this.kodetr = kodetr;
        this.kode = kode;
        this.nama = nama;
        this.satuan = satuan;
        this.qty = qty;
        this.harga = harga;
        this.subtotal = subtotal;
    }

    public DetailTransaksi(String kodetr, String kode, int qty, String nama, String satuan, double harga, double subtotal) {
        this.kodetr = kodetr;
        this.kode = kode;
        this.qty = qty;
        this.nama = nama;
        this.satuan = satuan;
        this.harga = harga;
        this.subtotal = subtotal;
    }

    public String getKodetr() { return kodetr; }
    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public String getSatuan() { return satuan; }
    public int getQty() { return qty; }
    public double getHarga() { return harga; }
    public double getSubtotal() { return subtotal; }
}