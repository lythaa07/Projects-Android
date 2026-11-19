package com.kelasp1.myfragmentt.transaksijual;

import androidx.fragment.app.Fragment;

public class Transaksi extends Fragment {
    private String kodetr;
    private String tanggal;
    private double grandtotal;

    public Transaksi(String kodetr, String tanggal, double grandtotal) {
        this.kodetr = kodetr;
        this.tanggal = tanggal;
        this.grandtotal = grandtotal;
    }

    public String getKodetr() { return kodetr; }
    public String getTanggal() { return tanggal; }
    public double getGrandtotal() { return grandtotal; }
}
