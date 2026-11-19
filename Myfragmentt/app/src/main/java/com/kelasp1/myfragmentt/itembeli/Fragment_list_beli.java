package com.kelasp1.myfragmentt.itembeli;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kelasp1.myfragmentt.R;
import com.kelasp1.myfragmentt.database.DBHelper;

public class Fragment_list_beli extends Fragment {
    private Button buttontambah;
    DBHelper dbHelper;
    RecyclerView recyclerViewisi;
    Adapterbeli adapterbeli; // Gunakan Adapterbeli, bukan Adapteritem

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_beli, container, false); // Pastikan layout ini sesuai

        buttontambah = view.findViewById(R.id.button1);
        recyclerViewisi = view.findViewById(R.id.recycleview);
        recyclerViewisi.setLayoutManager(new LinearLayoutManager(view.getContext()));

        dbHelper = new DBHelper(getContext());

        // Panggil readDatabeli() yang sudah dibuat di DBHelper
        adapterbeli = new Adapterbeli(dbHelper.readDatabeli(), getContext());
        recyclerViewisi.setAdapter(adapterbeli);

        buttontambah.setOnClickListener(v -> {
            // Arahkan ke Fragment untuk menambah data BELI
            Fragment_add_beli fragmentForm = new Fragment_add_beli();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragmentForm)
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }
}