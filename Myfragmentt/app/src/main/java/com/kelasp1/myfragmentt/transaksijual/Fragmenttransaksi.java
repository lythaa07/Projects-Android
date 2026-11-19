package com.kelasp1.myfragmentt.transaksijual;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kelasp1.myfragmentt.R;
import com.kelasp1.myfragmentt.database.DBHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Fragmenttransaksi extends Fragment {
    RecyclerView recyclerviewitem, recyclerviewtransaksi;
    DBHelper dbHelper;
    List<Item> itemList;
    ItemAdapteritem adapteritem;
    static ItemAdaptertransaksi adaptertransaksi;
    EditText tgl;
    static Button btnsave,btnclose;
    static TextView grandtotal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmenttransaksi, container, false);
        tgl = view.findViewById(R.id.editTextDate);
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = now.format(formatter);
        tgl.setText(formattedDate);
        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDropdown();
            }
        });
        grandtotal = view.findViewById(R.id.editTextgrandtotal);
        btnsave = view.findViewById(R.id.buttonsavetr);
        btnsave.setVisibility(View.GONE);
        btnsave.setOnClickListener(v -> {
            simpanTransaksi();
            Fragmenttransaksilist fragment = new Fragmenttransaksilist();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        btnclose = view.findViewById(R.id.btnclosetr);
        btnclose.setOnClickListener(v -> {
            Fragmenttransaksilist fragment = new Fragmenttransaksilist();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        recyclerviewitem = view.findViewById(R.id.recyclerViewitemitem);
        recyclerviewtransaksi = view.findViewById(R.id.recyclerViewtransaksi);

        dbHelper = new DBHelper(getContext());
        itemList = dbHelper.getAllItems(); // ambil data dari database

        // Adapter transaksi kosong dulu
        adaptertransaksi = new ItemAdaptertransaksi(new ArrayList<>(), getContext());
        recyclerviewtransaksi.setAdapter(adaptertransaksi);
        recyclerviewtransaksi.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adapter item dengan listener klik
        adapteritem = new ItemAdapteritem(itemList, getContext(), item -> {
            // Buat objek Itemtransaksi dari Item yang diklik
            Itemtransaksi transaksiBaru = new Itemtransaksi(
                    item.getkode(),
                    item.getNama(),
                    item.getSatuan(),
                    item.getHarga(),
                    1, // default qty
                    item.getHarga() // subtotal awal
            );
            adaptertransaksi.addItem(transaksiBaru);
            hitungGrandTotal();
        });

        recyclerviewitem.setAdapter(adapteritem);
        recyclerviewitem.setLayoutManager(new GridLayoutManager(getContext(), 3));

        return view;
    }
    private void showDatePickerDropdown() {
        // Fetch current system dates to serve as the picker defaults
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Instantiate the dialog with our spinner style theme resource reference
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                R.style.SpinnerDatePickerTheme,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format output structure (Note: Month array values start at 0)
                    String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, (selectedMonth + 1), selectedYear);
                    tgl.setText(formattedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    public static void  hitUlangQty(){

    }

    public static void hitungGrandTotal() {
        double total = 0;
        for (Itemtransaksi item : adaptertransaksi.getItemList()) {
            total += item.getSubtotal();
        }
        if(total>0)
        {
            btnsave.setVisibility(View.VISIBLE);
        }
        else
        {
            btnsave.setVisibility(View.GONE);
        }
        grandtotal.setText(String.valueOf(total));
    }
    private void simpanTransaksi() {
        String kodetr = "TRX" + System.currentTimeMillis(); // contoh kode unik
        String tanggal = tgl.getText().toString();
        double total = 0;

        for (Itemtransaksi item : adaptertransaksi.getItemList()) {
            total += item.getSubtotal();
        }

        // simpan header transaksi
        boolean headerSaved = dbHelper.insertTransaksi(kodetr, tanggal, total);

        if (headerSaved) {
            // simpan detail transaksi
            for (Itemtransaksi item : adaptertransaksi.getItemList()) {
                dbHelper.insertDetailTransaksi(
                        kodetr,
                        item.getKode(),
                        item.getQty(),
                        item.getHarga(),
                        item.getSubtotal()
                );
            }
            Toast.makeText(getContext(), "Transaksi berhasil disimpan", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Gagal menyimpan transaksi", Toast.LENGTH_SHORT).show();
        }
    }

}