package com.kelasp1.myfragmentt.itembeli;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kelasp1.myfragmentt.R;
import com.kelasp1.myfragmentt.database.DBHelper;


public class Fragment_add_beli extends Fragment {
    private Button buttonsimpan, buttontutup;
    private EditText inputKode, inputNama, inputSatuan, inputHarga;
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_beli, container, false);
        inputKode = view.findViewById(R.id.kode);
        inputNama = view.findViewById(R.id.nama);
        inputSatuan = view.findViewById(R.id.satuan);
        inputHarga = view.findViewById(R.id.harga);
        buttonsimpan = view.findViewById(R.id.buttonsave);
//        buttonhapus = view.findViewById(R.id.buttondelete);
        buttontutup = view.findViewById(R.id.buttonclose);
        dbHelper = new DBHelper(getContext());

        buttonsimpan.setOnClickListener(v -> {
            String kode = inputKode.getText().toString();
            String nama = inputNama.getText().toString();
            String satuan = inputSatuan.getText().toString();
            String harga = inputHarga.getText().toString();
            boolean inserted = dbHelper.insertDataBeli(kode, nama, satuan, Double.parseDouble(harga));
            if (inserted) {
                Toast.makeText(getActivity(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Gagal menyimpan data", Toast.LENGTH_SHORT).show();

            }
            Fragment_list_beli fragment01 = new Fragment_list_beli();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment01)
                    .commit();
        });


        buttontutup.setOnClickListener(v -> {
            Fragment_list_beli fragment01 = new Fragment_list_beli();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container , fragment01)
                    .commit();
        });
        return view;
    }

}