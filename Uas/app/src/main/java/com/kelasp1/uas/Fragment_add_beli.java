package com.kelasp1.uas;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Fragment_add_beli extends Fragment {
    private Button buttonsimpan, buttontutup;
    private EditText inputKode, inputNama, inputSatuan, inputHarga, inputDiskon, inputStok;
    DBHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_beli, container, false);
        // Inisialisasi View
        inputKode = view.findViewById(R.id.kode);
        inputNama = view.findViewById(R.id.nama);
        inputSatuan = view.findViewById(R.id.satuan);
        inputHarga = view.findViewById(R.id.harga);
        inputDiskon = view.findViewById(R.id.diskon);
        inputStok = view.findViewById(R.id.stok); // Pastikan ID di XML adalah @id/stok
        buttonsimpan = view.findViewById(R.id.buttonsave);
        buttontutup = view.findViewById(R.id.buttonclose);
        dbHelper = new DBHelper(getContext());
        buttonsimpan.setOnClickListener(v -> {
            try {
                String kode = inputKode.getText().toString();
                String nama = inputNama.getText().toString();
                String satuan = inputSatuan.getText().toString();
                // Konversi input
                double harga = Double.parseDouble(inputHarga.getText().toString());
                double diskon = Double.parseDouble(inputDiskon.getText().toString());
                int stok = Integer.parseInt(inputStok.getText().toString()); // Ambil sebagai int
                // Panggil insertDataBeli dengan 6 parameter (kode, nama, satuan, harga, diskon, stok)
                boolean inserted = dbHelper.insertDataBeli(kode, nama, satuan, harga, diskon, stok);
                if (inserted) {
                    Toast.makeText(getActivity(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new Fragment_list_beli())
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), "Pastikan semua angka diisi dengan benar", Toast.LENGTH_SHORT).show();
            }
        });
        buttontutup.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Fragment_list_beli())
                    .commit();
        });
        return view;
    }
}