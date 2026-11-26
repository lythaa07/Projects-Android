package com.kelasp1.quizmobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Deklarasi variabel
    private EditText etKode, etNama, etAlamat, etTelp, etUmur, etPendidikan;

    //deklarasi tombol dan RecyclerView
    private Button btnSave;
    private RecyclerView recyclerView;

    //Deklarasi variabel untuk database
    private FirebaseFirestore db;

    //Deklarasi variabel untuk data profil
    private List<Profil> profilList;

    //Deklarasi variabel untuk adapter
    private ProfilAdapter profilAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Penghubung ke database Firebase Firestore
        db = FirebaseFirestore.getInstance();

        //hubungin variabel kode java ke id xml
        etKode = findViewById(R.id.etKode);
        etNama = findViewById(R.id.etNama);
        etAlamat = findViewById(R.id.etAlamat);
        etTelp = findViewById(R.id.etTelp);
        etUmur = findViewById(R.id.etUmur);
        etPendidikan = findViewById(R.id.etPendidikan);

        //hubungin tombol save dan RecyclerView
        btnSave = findViewById(R.id.btnSave);
        recyclerView = findViewById(R.id.recyclerView);

        //mengatur pola di RecyclerView biar rapi
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        profilList = new ArrayList<>();

        // Inisialisasi Adapter dan pasang ke RecyclerView
        profilAdapter = new ProfilAdapter(profilList, db.collection("KuisData"));
        recyclerView.setAdapter(profilAdapter);

        // Aksi tombol SAVE untuk menyimpan data ke Firestore secara Realtime
        btnSave.setOnClickListener(v -> {
            String kode = etKode.getText().toString().trim();
            String nama = etNama.getText().toString().trim();
            String alamat = etAlamat.getText().toString().trim();
            String telp = etTelp.getText().toString().trim();
            String umur = etUmur.getText().toString().trim();
            String pendidikan = etPendidikan.getText().toString().trim();

            if (kode.isEmpty() || nama.isEmpty()) {
                Toast.makeText(MainActivity.this, "Kode dan Nama wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            // field data yang akan disimpan
            Map<String, Object> dataItem = new HashMap<>();
            dataItem.put("kode", kode);
            dataItem.put("nama", nama);
            dataItem.put("alamat", alamat);
            dataItem.put("telp", telp);
            dataItem.put("umur", umur);
            dataItem.put("pendidikan", pendidikan);

            // Simpan ke collection "KuisData" di Firestore
            db.collection("KuisData")
                    .document(kode)
                    .set(dataItem)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(MainActivity.this, "Data Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
                        kosongkanForm();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(MainActivity.this, "Gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        // Panggil fungsi untuk memuat data secara realtime dari Firestore
        loadDataRealtime();
    }

    private void kosongkanForm() {
        etKode.setText("");
        etNama.setText("");
        etAlamat.setText("");
        etTelp.setText("");
        etUmur.setText("");
        etPendidikan.setText("");
        etKode.requestFocus();
    }

    private void loadDataRealtime() {
        // Mendengarkan perubahan data secara Realtime dari Firestore
        db.collection("KuisData")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }

                    profilList.clear();
                    if (value != null) {
                        for (QueryDocumentSnapshot doc : value) {
                            Profil profil = doc.toObject(Profil.class);
                            profilList.add(profil);
                        }
                        profilAdapter.notifyDataSetChanged();
                    }
                });
    }
}