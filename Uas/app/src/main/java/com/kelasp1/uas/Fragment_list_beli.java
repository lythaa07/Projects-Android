package com.kelasp1.uas; // Sesuaikan dengan package Anda

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_list_beli extends Fragment {
    private Button buttontambah;
    DBHelper dbHelper;
    RecyclerView recyclerViewisi;
    Adapterbeli adapterbeli;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Pastikan R.layout.fragment_list_beli sesuai dengan file XML Anda
        View view = inflater.inflate(R.layout.fragment_list_beli, container, false);

        buttontambah = view.findViewById(R.id.button1);
        recyclerViewisi = view.findViewById(R.id.recycleview);
        recyclerViewisi.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DBHelper(getContext());

        // Memuat data dari database menggunakan method yang baru disesuaikan
        adapterbeli = new Adapterbeli(dbHelper.readDatabeli(), getContext());
        recyclerViewisi.setAdapter(adapterbeli);

        buttontambah.setOnClickListener(v -> {
            // Arahkan ke Fragment_add_beli
            Fragment_add_beli fragmentForm = new Fragment_add_beli();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragmentForm)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}