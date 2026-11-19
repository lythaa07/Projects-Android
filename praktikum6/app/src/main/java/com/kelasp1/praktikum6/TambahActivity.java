package com.kelasp1.praktikum6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TambahActivity extends AppCompatActivity {

    private EditText txtNIM, txtNama, txtProdi;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tambah2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtNIM = findViewById(R.id.txtNim);
        txtNama = findViewById(R.id.txtNama);
        txtProdi = findViewById(R.id.txtProdi);
        btnSimpan = findViewById(R.id.btnSimpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nim = txtNIM.getText().toString();
                String nama = txtNama.getText().toString();
                String prodi = txtProdi.getText().toString();

                DBHelper db = new DBHelper(TambahActivity.this);
                db.dml("INSERT INTO mahasiswa VALUES ('"+nim+"','"+nama+"','"+prodi+"')");
                Toast.makeText(TambahActivity.this, "Data Tersimpan!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}