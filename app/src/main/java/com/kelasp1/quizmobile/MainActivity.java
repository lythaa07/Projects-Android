package com.kelasp1.quizmobile;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText edKode, edNama, edAlamat, edTelp, edUmur, edPendidikan, edProvinsi, edKota, edKecamatan, edKelurahan;
    Button btnSimpan;
    RecyclerView recyclerView;
    List<Profil> profilList = new ArrayList<>();
    ProfilAdapter adapter;
    private CollectionReference profilRef;

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        profilRef = db.collection("profil");

        edKode = findViewById(R.id.etKode);
        edNama = findViewById(R.id.etNama);
        edAlamat = findViewById(R.id.etAlamat);
        edTelp = findViewById(R.id.etTelp);
        edUmur = findViewById(R.id.etUmur);
        edPendidikan = findViewById(R.id.etPendidikan);
        edProvinsi = findViewById(R.id.etProvinsi);
        edKota = findViewById(R.id.etKota);
        edKecamatan = findViewById(R.id.etKecamatan);
        edKelurahan = findViewById(R.id.etKelurahan);
        btnSimpan = findViewById(R.id.btnSave);
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new ProfilAdapter(profilList, db, this::loadProfilFromApi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Ambil data dari API Laravel
        loadProfilFromApi();

        btnSimpan.setOnClickListener(v -> {
            saveData();
        });
    }

    private void saveData() {
        String kode = edKode.getText().toString();
        String nama = edNama.getText().toString();
        String alamat = edAlamat.getText().toString();
        String telp = edTelp.getText().toString();
        String umurstr = edUmur.getText().toString();
        String pendidikan = edPendidikan.getText().toString();
        String provinsi = edProvinsi.getText().toString();
        String kota = edKota.getText().toString();
        String kecamatan = edKelurahan.getText().toString();
        String kelurahan = edKelurahan.getText().toString();

        if (kode.isEmpty() || nama.isEmpty() || alamat.isEmpty() || telp.isEmpty() || umurstr.isEmpty() || pendidikan.isEmpty()) {
            Toast.makeText(MainActivity.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        int umur = Integer.parseInt(umurstr);
        Profil profil = new Profil(kode, nama, alamat, telp, umur, pendidikan, provinsi, kota, kecamatan, kelurahan);

        // Simpan ke Laravel API
        RetrofitClient.getApiService().saveProfil(profil).enqueue(new Callback<Profil>() {
            @Override
            public void onResponse(Call<Profil> call, Response<Profil> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Berhasil simpan ke API Laravel", Toast.LENGTH_SHORT).show();
                    loadProfilFromApi(); // Refresh list setelah simpan
                    clearForm();
                }
            }

            @Override
            public void onFailure(Call<Profil> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });

        // Simpan ke Firestore
        profilRef.document(kode).set(profil);
    }

    private void loadProfilFromApi() {
        RetrofitClient.getApiService().getProfil().enqueue(new Callback<ProfilResponse>() {
            @Override
            public void onResponse(Call<ProfilResponse> call, Response<ProfilResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Mengambil data dari dalam objek pembungkus "data"
                    List<Profil> dataDariApi = response.body().getData();
                    if (dataDariApi != null) {
                        profilList.clear();
                        profilList.addAll(dataDariApi);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("API_ERROR", "Gagal: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ProfilResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal ambil data API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm() {
        edKode.setText("");
        edNama.setText("");
        edAlamat.setText("");
        edTelp.setText("");
        edUmur.setText("");
        edPendidikan.setText("");
        edProvinsi.setText("");
        edKota.setText("");
        edKecamatan.setText("");
        edKelurahan.setText("");
    }
}
