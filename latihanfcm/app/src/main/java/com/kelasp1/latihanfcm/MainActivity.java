package com.kelasp1.latihanfcm;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etKodeItem, etNamaItem, etSatuan, etHarga;
    private Button btnSimpan, btnCopyToken;
    private RecyclerView recyclerViewItems;

    private FirebaseFirestore db;
    private String fcmToken = "";

    private ItemAdapter itemAdapter;
    private List<ItemModel> itemModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        etKodeItem = findViewById(R.id.etKodeItem);
        etNamaItem = findViewById(R.id.etNamaItem);
        etSatuan = findViewById(R.id.etSatuan);
        etHarga = findViewById(R.id.etHarga);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnCopyToken = findViewById(R.id.btnCopyToken);
        recyclerViewItems = findViewById(R.id.recyclerViewItems);

        // Setup RecyclerView
        itemModelList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemModelList);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItems.setAdapter(itemAdapter);

        fetchFCMToken();

        btnSimpan.setOnClickListener(v -> saveDataToFirestore());

        btnCopyToken.setOnClickListener(v -> {
            if (!fcmToken.isEmpty()) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("FCM Token", fcmToken);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "FCM Token berhasil disalin!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Token belum siap, coba lagi.", Toast.LENGTH_SHORT).show();
            }
        });

        loadItemsFromFirestore();
    }

    private void fetchFCMToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        fcmToken = task.getResult();
                    }
                });
    }

    private void saveDataToFirestore() {
        String kode = etKodeItem.getText().toString().trim();
        String nama = etNamaItem.getText().toString().trim();
        String satuan = etSatuan.getText().toString().trim();
        String hargaStr = etHarga.getText().toString().trim();

        if (kode.isEmpty() || nama.isEmpty() || satuan.isEmpty() || hargaStr.isEmpty()) {
            Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        double harga = Double.parseDouble(hargaStr);

        Map<String, Object> item = new HashMap<>();
        item.put("kode", kode);
        item.put("nama", nama);
        item.put("satuan", satuan);
        item.put("harga", harga);

        db.collection("items")
                .add(item)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show();
                    etKodeItem.setText("");
                    etNamaItem.setText("");
                    etSatuan.setText("");
                    etHarga.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Gagal menyimpan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loadItemsFromFirestore() {
        db.collection("items").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("FIRESTORE", "Listen failed.", error);
                return;
            }

            if (value != null) {
                itemModelList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    ItemModel item = doc.toObject(ItemModel.class);
                    itemModelList.add(item);
                }
                itemAdapter.notifyDataSetChanged();
            }
        });
    }
}