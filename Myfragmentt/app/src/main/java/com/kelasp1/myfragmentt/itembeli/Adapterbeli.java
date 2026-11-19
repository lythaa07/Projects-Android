package com.kelasp1.myfragmentt.itembeli;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelasp1.myfragmentt.R;
import com.kelasp1.myfragmentt.itembeli.Adapterbeli;
import com.kelasp1.myfragmentt.itembeli.item_beli;

import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

public class Adapterbeli extends RecyclerView.Adapter<Adapterbeli.ViewHolder> {
    private List<item_beli> itemList;
    private Context context;
    public Adapterbeli (List<item_beli> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapterbeli.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_beli, parent, false);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapterbeli.ViewHolder holder, int position) {
        item_beli item = itemList.get(position);
        NumberFormat formatRupiah = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        String hargaTerformat = "Rp " + formatRupiah.format(item.getHarga());

        holder.kode1.setText(item.getKode());
        holder.nama1.setText(item.getNama());
        holder.satuan1.setText(item.getSatuan());
        holder.harga1.setText(String.valueOf(item.getHarga()));



// Aksi ketika item di list dipencet
        holder.itemView.setOnClickListener(v -> {
            Fragment_update_beli fragment03 = new Fragment_update_beli();
            Bundle bundle = new Bundle();
            bundle.putString("kode", item.getKode());
            bundle.putString("nama", item.getNama());
            bundle.putString("satuan", item.getSatuan());
            bundle.putDouble("harga", item.getHarga());
            fragment03.setArguments(bundle);

            if (context instanceof androidx.appcompat.app.AppCompatActivity) {
                ((androidx.appcompat.app.AppCompatActivity) context).getSupportFragmentManager()
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
        TextView kode1, nama1, satuan1, harga1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kode1 = itemView.findViewById(R.id.textView1);
            nama1 = itemView.findViewById(R.id.textView2);
            satuan1 = itemView.findViewById(R.id.textView3);
            harga1 = itemView.findViewById(R.id.textView4);
        }
    }
}