package com.kelasp1.myfragmentt.transaksijual;

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
import java.util.ArrayList;
import java.util.List;

public class Fragmentitemlist extends Fragment {

    RecyclerView recyclerViewitem;
    DBHelper dbHelper;
    Button btntambah;
    List<Item> itemList;
    ItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmentitemlist, container, false);

        btntambah = view.findViewById(R.id.buttontambah);
        recyclerViewitem = view.findViewById(R.id.recyclerViewitemitem);

        recyclerViewitem.setLayoutManager(new LinearLayoutManager(getContext()));

        btntambah.setOnClickListener(v -> {
            // Pastikan Fragmentitem sudah dibuat di package yang benar
            Fragmentitem fragment = new Fragmentitem();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment) // Pastikan R.id.fragmentisi ada di activity_main
                    .addToBackStack(null)
                    .commit();
        });

        dbHelper = new DBHelper(getContext());
        itemList =   dbHelper.getAllItems();

        // Pastikan ItemAdapter menerima parameter yang sesuai
        adapter = new ItemAdapter(itemList, getContext());
        recyclerViewitem.setAdapter(adapter);

        return view;
    }
}