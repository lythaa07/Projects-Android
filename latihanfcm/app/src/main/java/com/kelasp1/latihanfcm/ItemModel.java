package com.kelasp1.latihanfcm;

public class ItemModel {
    private String kode, nama, satuan;
    private double harga;

    public ItemModel() {}

    public ItemModel(String kode, String nama, String satuan, double harga) {
        this.kode = kode;
        this.nama = nama;
        this.satuan = satuan;
        this.harga = harga;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public String getSatuan() { return satuan; }
    public double getHarga() { return harga; }
}