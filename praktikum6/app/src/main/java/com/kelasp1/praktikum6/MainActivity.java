package com.kelasp1.praktikum6;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ArrayList<Mahasiswa> data = new ArrayList<>();
    private Adaptermhs adaptermhs;
    private Button btnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rv = findViewById(R.id.rv);
        btnTambah =findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, TambahActivity.class);
                        startActivity(intent);
            }
        });

        adaptermhs = new Adaptermhs(data, MainActivity.this);
        RecyclerView.LayoutManager layRV = new LinearLayoutManager(MainActivity.this);
        rv.setLayoutManager(layRV);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adaptermhs);

//        setData("01", "Rampa", "Sistem Informasi");
//        setData("02", "Tulus", "Sistem Informasi");
//        setData("03", "Atika", "Sistem Informasi");


    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper db= new DBHelper(MainActivity.this);
        int baris = Integer.parseInt(
                db.cari("select count(*) as jml from mahasiswa")
        );
        data.clear();
        String temp[][] = db.cari_array("select * from mahasiswa", baris, 3);
        for(int i=0; i<temp.length; i++){
            setData(temp[i][0], temp[i][1], temp[i][2]);
        }

    }

    private void setData(String nim, String  nama, String  prodi) {
        Mahasiswa obj = new Mahasiswa(nim, nama, prodi);

        data.add(obj);
        adaptermhs.notifyDataSetChanged();
    }
}