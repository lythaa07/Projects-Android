package com.kelasp1.myfragmentt.transaksijual;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kelasp1.myfragmentt.R; // Import R dari package utama
import com.kelasp1.myfragmentt.database.DBHelper; // Sesuaikan lokasi DBHelper Anda

public class Fragmentitem extends Fragment {
    EditText kode, nama, satuan, harga;
    Button btnSave, btnclose;
    ImageView imageView;
    Uri selectedImageUri;
    private static final int PICK_IMAGE = 100;
    DBHelper dbHelper;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragmentitem, container, false);

        kode = view.findViewById(R.id.editkode);
        nama = view.findViewById(R.id.editnama);
        satuan = view.findViewById(R.id.editsatuan);
        harga = view.findViewById(R.id.editharga);
        btnSave = view.findViewById(R.id.buttonsave);
        btnclose = view.findViewById(R.id.buttonclose);
        imageView = view.findViewById(R.id.imageView);

        dbHelper = new DBHelper(getContext());

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        btnclose.setOnClickListener(v -> {
            Fragmentitemlist fragment = new Fragmentitemlist();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        btnSave.setOnClickListener(v -> {
            String kode1 = kode.getText().toString().trim();
            String nama1 = nama.getText().toString().trim();
            String satuan1 = satuan.getText().toString().trim();
            String harga1 = harga.getText().toString().trim();

            if (!kode1.isEmpty() && selectedImageUri != null && !harga1.isEmpty()) {
                String imagePath = getRealPathFromURI(selectedImageUri);
                boolean inserted = dbHelper.insertData(kode1, nama1, satuan1, Double.parseDouble(harga1), imagePath);
                if (inserted) {
                    Toast.makeText(getContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    // Reset fields
                    kode.setText(""); nama.setText(""); satuan.setText(""); harga.setText("");
                    imageView.setImageResource(0);
                    selectedImageUri = null;
                } else {
                    Toast.makeText(getContext(), "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Semua field dan gambar harus diisi", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
        }
    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }
}