package com.kelasp1.myfragmentt.itemjual;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kelasp1.myfragmentt.R;
import com.kelasp1.myfragmentt.database.DBHelper;


public class Fragment_add_jual extends Fragment {
    private Button buttonsimpan, buttontutup;
    private ImageView imageView;
    private EditText inputKode, inputNama, inputSatuan, inputHarga;
    private Uri selectedImage;
    private final ActivityResultLauncher<String> imagePickerLaunch =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(), o -> {
                        if (o != null) {
                            selectedImage = o;
                            imageView.setImageURI(o);
                        }
                    }
            );
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_jual, container, false);
        inputKode = view.findViewById(R.id.kode);
        inputNama = view.findViewById(R.id.nama);
        inputSatuan = view.findViewById(R.id.satuan);
        inputHarga = view.findViewById(R.id.harga);
        buttonsimpan = view.findViewById(R.id.buttonsave);
        imageView = view.findViewById(R.id.iv_image_preview);
//        buttonhapus = view.findViewById(R.id.buttondelete);
        buttontutup = view.findViewById(R.id.buttonclose);
        dbHelper = new DBHelper(getContext());

        buttonsimpan.setOnClickListener(v -> {
            String kode = inputKode.getText().toString();
            String nama = inputNama.getText().toString();
            String satuan = inputSatuan.getText().toString();
            String harga = inputHarga.getText().toString();
            requireContext().getContentResolver().takePersistableUriPermission(
                    selectedImage,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );
            String imageUriString = selectedImage.toString();
            boolean inserted = dbHelper.insertData(kode, nama, satuan, Double.parseDouble(harga), imageUriString);
            if (inserted) {
                Toast.makeText(getActivity(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Gagal menyimpan data", Toast.LENGTH_SHORT).show();

            }
            Fragment_list_jual fragment01 = new Fragment_list_jual();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment01)
                    .commit();
        });

        imageView.setOnClickListener(v -> {
            imagePickerLaunch.launch("image/*");
        });

        buttontutup.setOnClickListener(v -> {
            Fragment_list_jual fragment01 = new Fragment_list_jual();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container , fragment01)
                    .commit();
        });
        return view;
    }

}