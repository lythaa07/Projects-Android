package com.kelasp1.latihanfirebase01;

import android.content.Intent;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseReference dbRef;
    EditText etKode, etNama, etSatuan, etHarga;
    Button btnSave, btnLogout;
    RecyclerView recyclerView;
    List<Item> itemList = new ArrayList<>();
    ItemAdapter adapter;
    FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Inisialisasi Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // TAMBAHAN: Cek apakah user sudah login atau belum
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }
        FirebaseApp.initializeApp(this);
        dbRef = FirebaseDatabase.getInstance("https://latihan01-13c50-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("items").child(mAuth.getUid());
        etKode = findViewById(R.id.etKode);
        etNama = findViewById(R.id.etNama);
        etSatuan = findViewById(R.id.etSatuan);
        etHarga = findViewById(R.id.etHarga);
        btnSave = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnLogout);
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new ItemAdapter(itemList, dbRef);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FirebaseUser user = mAuth.getCurrentUser();
        checkAuth();
        btnSave.setOnClickListener(v -> {
            user.reload().addOnCompleteListener(task -> {

                if (!task.isSuccessful()) {
                    // Jika akun sudah dihapus di Firebase Auth Console
                    mAuth.signOut();
                    Toast.makeText(MainActivity.this, "Akun Anda sudah tidak terdaftar!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    return;
                }


                String kode = etKode.getText().toString();
                String nama = etNama.getText().toString();
                String satuan = etSatuan.getText().toString();

                if (kode.isEmpty() || nama.isEmpty() || satuan.isEmpty() || etHarga.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                int harga = Integer.parseInt(etHarga.getText().toString());

                Item item = new Item(kode, nama, satuan, harga);
                dbRef.child(kode).setValue(item)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(MainActivity.this, "Item berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            // Mengosongkan form setelah berhasil disimpan
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

        });

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                itemList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Item item = data.getValue(Item.class);
                    itemList.add(item);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
        // Tombol Logout
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut(); // Keluar dari akun Firebase / Google
            mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); ;
            });

        });
    }
    private void checkAuth() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            return;
        }
        user.getIdToken(true)
                .addOnFailureListener(e -> {
                    // Credential sudah tidak valid
                    mAuth.signOut();
                    mGoogleSignInClient.signOut();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); ;
                });
    }
}