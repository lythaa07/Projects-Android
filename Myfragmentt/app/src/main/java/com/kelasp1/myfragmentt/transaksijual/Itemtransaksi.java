package com.kelasp1.myfragmentt.transaksijual;

public class Itemtransaksi {
    private String kode;
    private String nama;
    private String satuan;
    private Double harga;
    private int qty;
    private Double subtotal;

    public Itemtransaksi() {
    }

    public Itemtransaksi(String kode, String nama, String satuan, Double harga, int qty, Double subtotal) {
        this.kode = kode;
        this.nama = nama;
        this.satuan = satuan;
        this.harga = harga;
        this.qty = qty;
        this.subtotal = subtotal;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    public void decreaseQty(){
        this.qty--;
    }
}