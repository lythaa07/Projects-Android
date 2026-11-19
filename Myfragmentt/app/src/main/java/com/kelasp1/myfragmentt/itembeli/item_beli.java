package com.kelasp1.myfragmentt.itembeli;

public class item_beli {
    String kode;
    String nama;
    String satuan;
    Double harga;
    public item_beli(String kode, String nama, String satuan, Double harga) {
        this.kode = kode;
        this.nama = nama;
        this.satuan = satuan;
        this.harga = harga;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getSatuan() {
        return satuan;
    }
    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
    public Double getHarga() {
        return harga;
    }
    public void setHarga(Double harga) {
        this.harga = harga;
    }

}
