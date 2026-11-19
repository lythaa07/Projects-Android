package com.kelasp1.myfragmentt.itemjual;
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

public class Fragment_list_jual extends Fragment {
    private Button buttontambah;
    DBHelper dbHelper;
    RecyclerView recyclerViewisi;
    Adapteritem adapteritem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_jual, container, false);
        buttontambah = view.findViewById(R.id.button1);
        recyclerViewisi = view.findViewById(R.id.recycleview);
        recyclerViewisi.setLayoutManager(new LinearLayoutManager(view.getContext()));
        dbHelper = new DBHelper(getContext());
        adapteritem = new Adapteritem(dbHelper.readData(), getContext());
        recyclerViewisi.setAdapter(adapteritem);

        buttontambah.setOnClickListener(v -> {
            Fragment_add_jual fragmentForm = new Fragment_add_jual();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragmentForm)
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }
}

