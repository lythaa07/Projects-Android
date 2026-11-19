package com.kelasp1.uas;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Fragment_update_beli extends Fragment {
    private Button btnUpdate, btnDelete, btnClose;
    private EditText inputKode, inputNama, inputSatuan, inputHarga, inputDiskon, inputStok; // Ganti inputGudang ke inputStok
    DBHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_beli, container, false);
        // Inisialisasi View
        inputKode = view.findViewById(R.id.kode);
        inputNama = view.findViewById(R.id.nama);
        inputSatuan = view.findViewById(R.id.satuan);
        inputHarga = view.findViewById(R.id.harga);
        inputDiskon = view.findViewById(R.id.diskon);
        inputStok = view.findViewById(R.id.stok); // Pastikan ID di XML adalah @id/stok
        btnUpdate = view.findViewById(R.id.buttonsave);
        btnDelete = view.findViewById(R.id.buttondelete);
        btnClose = view.findViewById(R.id.buttonclose);
        dbHelper = new DBHelper(getContext());
        // Ambil data dari Bundle
        if (getArguments() != null) {
            inputKode.setText(getArguments().getString("kode"));
            inputNama.setText(getArguments().getString("nama"));
            inputSatuan.setText(getArguments().getString("satuan"));
            inputHarga.setText(String.valueOf(getArguments().getDouble("harga")));
            inputDiskon.setText(String.valueOf(getArguments().getDouble("diskon")));
            inputStok.setText(String.valueOf(getArguments().getInt("stok"))); // Ambil int
            inputKode.setEnabled(false); // Kode/ID tidak bisa diubah
        }
        btnUpdate.setOnClickListener(v -> {
            try {
                String nama = inputNama.getText().toString();
                String satuan = inputSatuan.getText().toString();
                double harga = Double.parseDouble(inputHarga.getText().toString());
                double diskon = Double.parseDouble(inputDiskon.getText().toString());
                int stok = Integer.parseInt(inputStok.getText().toString()); // Ambil sebagai int
                // Update dengan parameter baru (kode, nama, satuan, harga, diskon, stok)
                boolean updated = dbHelper.updateDatabeli(
                        inputKode.getText().toString(),
                        nama,
                        satuan,
                        harga,
                        diskon,
                        stok
                );
                if (updated) {
                    Toast.makeText(getContext(), "Data diperbarui", Toast.LENGTH_SHORT).show();
                    kembaliKeHalamanUtama();
                } else {
                    Toast.makeText(getContext(), "Gagal diperbarui", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Mohon isi angka dengan benar", Toast.LENGTH_SHORT).show();
            }
        });
        btnDelete.setOnClickListener(v -> {
            boolean deleted = dbHelper.deleteDatabeli(inputKode.getText().toString());
            if (deleted) {
                Toast.makeText(getContext(), "Data dihapus", Toast.LENGTH_SHORT).show();
                kembaliKeHalamanUtama();
            } else {
                Toast.makeText(getContext(), "Gagal hapus", Toast.LENGTH_SHORT).show();
            }
        });
        btnClose.setOnClickListener(v -> kembaliKeHalamanUtama());
        return view;
    }
    private void kembaliKeHalamanUtama() {
        if (getParentFragmentManager() != null) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment_list_beli())
                    .commit();
        }
    }
}