package com.kelasp1.latihanfirestore03;

import android.os.Bundle;
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

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CollectionReference dbRef;
    EditText etKode, etNama, etSatuan, etHarga;
    Button btnSave;
    RecyclerView recyclerView;
    List<Item> itemList = new ArrayList<>();
    ItemAdapter adapter;

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

        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        dbRef = db.collection("items");

        etKode = findViewById(R.id.etKode);
        etNama = findViewById(R.id.etNama);
        etSatuan = findViewById(R.id.etSatuan);
        etHarga = findViewById(R.id.etHarga);
        btnSave = findViewById(R.id.btnSave);
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new ItemAdapter(itemList, dbRef);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            String kode = etKode.getText().toString().trim();
            String nama = etNama.getText().toString().trim();
            String satuan = etSatuan.getText().toString().trim();

            if (kode.isEmpty() || nama.isEmpty() || satuan.isEmpty() || etHarga.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            int harga = Integer.parseInt(etHarga.getText().toString());

            Item item = new Item(kode, nama, satuan, harga);
            dbRef.document(kode).set(item)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(MainActivity.this, "Item berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        etKode.setText("");
                        etNama.setText("");
                        etSatuan.setText("");
                        etHarga.setText("");
                        etKode.requestFocus();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(MainActivity.this, "Gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });
        dbRef.addSnapshotListener((value, error) -> {
            if (error != null) return;
            if (value != null) {
                itemList.clear();
                for (DocumentSnapshot data : value.getDocuments()) {
                    Item item = data.toObject(Item.class);
                    if (item != null) {
                        itemList.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}