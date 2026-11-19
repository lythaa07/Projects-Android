package com.kelasp1.myfragmentt.itemjual;
public class Item_jual {

    String kode;
    String nama;
    String satuan;
    Double harga;
    String imgPath;
    public Item_jual(String kode, String nama, String satuan, Double harga, String imgPath) {
        this.kode = kode;
        this.nama = nama;
        this.satuan = satuan;
        this.harga = harga;
        this.imgPath = imgPath;
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

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }
}