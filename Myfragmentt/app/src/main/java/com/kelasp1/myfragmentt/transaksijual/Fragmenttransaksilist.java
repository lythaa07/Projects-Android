package com.kelasp1.myfragmentt.transaksijual;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kelasp1.myfragmentt.R;
import com.kelasp1.myfragmentt.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class Fragmenttransaksilist extends Fragment {
    RecyclerView recyclerViewtransaksi;
    DBHelper dbHelper;
    Button btntambah;
    List<Transaksi> transaksiList;
    TransaksiAdapter adapter;
    static TextView grandtotal1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmenttransaksilist, container, false);

        btntambah = view.findViewById(R.id.buttontambah);
        grandtotal1 = view.findViewById(R.id.editTexttotal);
        recyclerViewtransaksi = view.findViewById(R.id.recyclerViewtransaksi);

        recyclerViewtransaksi.setLayoutManager(new LinearLayoutManager(getContext()));

        btntambah.setOnClickListener(v -> {
            // Pastikan Fragmenttransaksi ada di package yang sama atau sudah di-import
            Fragmenttransaksi fragment = new Fragmenttransaksi();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        dbHelper = new DBHelper(getContext());
        transaksiList = dbHelper.getAllTransaksi();

        if (transaksiList == null) {
            transaksiList = new ArrayList<>();
        }

        adapter = new TransaksiAdapter(transaksiList, getContext(), new TransaksiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Transaksi transaksi) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, FragmentDetailTransaksi.newInstance(transaksi))
                        .commit();
            }
        });
        recyclerViewtransaksi.setAdapter(adapter);

        hitungGrandTotal();
        return view;
    }

    public void hitungGrandTotal() {
        double total = 0;
        // Pastikan adapter.getTransaksiList() tidak mengembalikan null
        List<Transaksi> list = adapter.getTransaksiList();
        if (list != null) {
            for (Transaksi transaksi : list) {
                total += transaksi.getGrandtotal();
            }
        }
        grandtotal1.setText(String.valueOf(total));
    }
}