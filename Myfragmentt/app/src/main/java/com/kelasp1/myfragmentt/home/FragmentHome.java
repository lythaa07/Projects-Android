package com.kelasp1.myfragmentt.home;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kelasp1.myfragmentt.R;
import com.kelasp1.myfragmentt.database.DBHelper;

public class FragmentHome extends Fragment {
    private DBHelper helper;
    private SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        helper = new DBHelper(getContext());
        database = helper.getWritableDatabase();

        TextView lblTitle = view.findViewById(R.id.pos_litha);
        lblTitle.setText("POS Litha");

        return view;
    }
}