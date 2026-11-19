package com.kelasp1.uas;

public class item_beli {
    private String kode;
    private String nama;
    private String satuan;
    private Double harga;
    private Double diskon;
    private int stok;

    // Konstruktor disesuaikan
    public item_beli(String kode, String nama, String satuan, Double harga, Double diskon, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.satuan = satuan;
        this.harga = harga;
        this.diskon = diskon;
        this.stok = stok;
    }

    // Getter dan Setter
    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getSatuan() { return satuan; }
    public void setSatuan(String satuan) { this.satuan = satuan; }

    public Double getHarga() { return harga; }
    public void setHarga(Double harga) { this.harga = harga; }

    public Double getDiskon() { return diskon; }
    public void setDiskon(Double diskon) { this.diskon = diskon; }

    // Getter dan Setter untuk stok
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
}