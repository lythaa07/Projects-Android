package com.kelasp1.myfragmentt.transaksijual;

public class Item {
    private String kode;
    private String nama;

    private String satuan;

    private Double harga;
    private String image;

    public Item() {
    }

    public Item(String kode, String nama, String satuan, Double harga, String image) {
        this.kode = kode;
        this.nama = nama;
        this.satuan = satuan;
        this.harga = harga;
        this.image = image;
    }

    public String getkode() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
