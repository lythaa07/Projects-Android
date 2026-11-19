package com.kelasp1.pertemuan07;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.kelasp1.pertemuan07.databinding.ActivityHistoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private HistoryAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    // [TAMBAH] Cache produk (id -> Product) untuk join ke daftar riwayat.
    private final Map<String, Product> productMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        setupRecyclerView();
        loadProducts();
        loadOrderHistory();
    }

    private void setupRecyclerView() {
        adapter = new HistoryAdapter();
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.rvHistory.setAdapter(adapter);
    }

    private void loadOrderHistory() {
        String currentUserId = mAuth.getUid();
        if (currentUserId == null) {
            Toast.makeText(this, "Pengguna belum login!", Toast.LENGTH_SHORT).show();
            return;
        }

        // [DIUBAH] Tanpa orderBy() agar tidak butuh composite index di Firestore.
        // whereEqualTo() pada satu field dilayani index otomatis.
        // Pengurutan dilakukan manual di bawah (terbaru ke atas).
        db.collection("orders")
                .whereEqualTo("userId", currentUserId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(HistoryActivity.this, "Gagal memuat riwayat: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (value != null) {
                        List<Order> orders = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            Order order = doc.toObject(Order.class);
                            order.setId(doc.getId());
                            orders.add(order);
                        }

                        // Urutkan terbaru ke atas; timestamp null (@ServerTimestamp
                        // yang belum terisi) ditaruh paling bawah.
                        orders.sort((a, b) -> {
                            Date ta = a.getTimestamp();
                            Date tb = b.getTimestamp();
                            if (ta == null && tb == null) return 0;
                            if (ta == null) return 1;
                            if (tb == null) return -1;
                            return tb.compareTo(ta);
                        });

                        adapter.setProductMap(productMap);
                        adapter.setOrderList(orders);
                    }
                });
    }

    // [TAMBAH] Mengambil koleksi /products sekali (get) untuk keperluan join
    // nama & harga produk pada daftar riwayat.
    private void loadProducts() {
        db.collection("products").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Product product = doc.toObject(Product.class);
                        product.setId(doc.getId());
                        productMap.put(product.getId(), product);
                    }
                    // Produk mungkin tiba setelah riwayat, jadi render ulang.
                    adapter.setProductMap(productMap);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Gagal memuat produk: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}