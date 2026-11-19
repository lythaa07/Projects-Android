package com.kelasp1.uas;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Adapterbeli extends RecyclerView.Adapter<Adapterbeli.ViewHolder> {
    private List<item_beli> itemList;
    private Context context;
    public Adapterbeli(List<item_beli> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_beli, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        item_beli item = itemList.get(position);
        NumberFormat formatRupiah = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        String hargaTerformat = "Rp " + formatRupiah.format(item.getHarga());
        holder.kode1.setText(item.getKode());
        holder.nama1.setText(item.getNama());
        holder.satuan1.setText(item.getSatuan());
        holder.harga1.setText(hargaTerformat);
        holder.diskon1.setText("Disc: " + item.getDiskon() + "%");
        holder.stok1.setText("Stok: " + item.getStok()); // Menampilkan Stok
        holder.itemView.setOnClickListener(v -> {
            Fragment_update_beli fragment03 = new Fragment_update_beli();
            Bundle bundle = new Bundle();
            bundle.putString("kode", item.getKode());
            bundle.putString("nama", item.getNama());
            bundle.putString("satuan", item.getSatuan());
            bundle.putDouble("harga", item.getHarga());
            bundle.putDouble("diskon", item.getDiskon());
            bundle.putInt("stok", item.getStok()); // Kirim Stok
            fragment03.setArguments(bundle);
            if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment03)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kode1, nama1, satuan1, harga1, diskon1, stok1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kode1 = itemView.findViewById(R.id.textView1);
            nama1 = itemView.findViewById(R.id.textView2);
            satuan1 = itemView.findViewById(R.id.textView3);
            harga1 = itemView.findViewById(R.id.textView4);
            diskon1 = itemView.findViewById(R.id.textViewDiskon);
            stok1 = itemView.findViewById(R.id.textViewStok);
        }
    }
}