package com.kelasp1.myfragmentt.transaksijual;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelasp1.myfragmentt.R;

import java.io.File;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> itemList;
    private Context context;

    public ItemAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.kodeItem.setText(item.getkode());
        holder.namaItem.setText(item.getNama());
        holder.satuanItem.setText(item.getSatuan());
        // Menggunakan String.valueOf() lebih aman untuk tipe data numerik
        holder.hargaItem.setText(String.valueOf(item.getHarga()));

        // Logika loading gambar yang lebih aman
        if (item.getImage() != null && !item.getImage().isEmpty()) {
            File imgFile = new File(item.getImage());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imgItem.setImageBitmap(myBitmap);
            } else {
                // Gambar tidak ditemukan di path, pasang gambar placeholder/default
                holder.imgItem.setImageResource(R.drawable.ic_launcher_background);
            }
        } else {
            // Path null atau kosong, pasang gambar default
            holder.imgItem.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView kodeItem, namaItem, satuanItem, hargaItem;
        ImageView imgItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kodeItem = itemView.findViewById(R.id.textViewkode);
            namaItem = itemView.findViewById(R.id.textViewtanggal); // Pastikan ini sudah sesuai dengan ID di layout_item
            satuanItem = itemView.findViewById(R.id.textViewgrantotal); // Pastikan ini sudah sesuai dengan ID di layout_item
            hargaItem = itemView.findViewById(R.id.textViewharga);
            imgItem = itemView.findViewById(R.id.imageViewitem);
        }
    }
}