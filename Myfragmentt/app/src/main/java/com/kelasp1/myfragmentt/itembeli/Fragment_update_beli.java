package com.kelasp1.myfragmentt.itembeli;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kelasp1.myfragmentt.R;
import com.kelasp1.myfragmentt.database.DBHelper;

public class Fragment_update_beli extends Fragment {
    private Button btnUpdate, btnDelete, btnClose;
    private EditText inputKode, inputNama, inputSatuan, inputHarga;

    DBHelper dbHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_beli, container, false);
// Inisialisasi View
        inputKode = view.findViewById(R.id.kode);
        inputNama = view.findViewById(R.id.nama);
        inputSatuan = view.findViewById(R.id.satuan);
        inputHarga = view.findViewById(R.id.harga);
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
            inputKode.setEnabled(false); // Kode tidak bisa diubah karena Primary Key
        }
        btnUpdate.setOnClickListener(v -> {
            String nama = inputNama.getText().toString();
            String satuan = inputSatuan.getText().toString();
            String hargaStr = inputHarga.getText().toString();
            if (nama.isEmpty() || hargaStr.isEmpty()) {
                Toast.makeText(getContext(), "Mohon isi semua data", Toast.LENGTH_SHORT).show();
            } else {
                boolean updated = dbHelper.updateDatabeli(inputKode.getText().toString(), nama, satuan, Double.parseDouble(hargaStr));
                if (updated) {
                    Toast.makeText(getContext(), "Data diperbarui", Toast.LENGTH_SHORT).show();
                    kembaliKeHalamanUtama();
                } else {
                    Toast.makeText(getContext(), "Gagal diperbarui", Toast.LENGTH_SHORT).show();
                }
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
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment_list_beli())
                    .commit();
        }
    }
}

