package com.kelasp1.quizmobile;

public class Profil {
    private String kode;
    private String nama;
    private String alamat;
    private String telp;
    private String umur;
    private String pendidikan;
    private String provinsi;
    private String kota;
    private String kecamatan;
    private String kelurahan;

    public Profil() {
    }

    public Profil(String kode, String nama, String alamat, String telp, String umur, String pendidikan, String provinsi, String kota, String kecamatan, String kelurahan) {
        this.kode = kode;
        this.nama = nama;
        this.alamat = alamat;
        this.telp = telp;
        this.umur = umur;
        this.pendidikan = pendidikan;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }
}