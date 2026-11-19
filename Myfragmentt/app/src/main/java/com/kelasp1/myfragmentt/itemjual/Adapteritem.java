package com.kelasp1.myfragmentt.itemjual;

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

import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

public class Adapteritem extends RecyclerView.Adapter<Adapteritem.ViewHolder> {
    private List<Item_jual> itemList;
    private Context context;
    public Adapteritem(List<Item_jual> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapteritem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapteritem.ViewHolder holder, int position) {
        Item_jual item = itemList.get(position);
        NumberFormat formatRupiah = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        String hargaTerformat = "Rp " + formatRupiah.format(item.getHarga());

        holder.kode1.setText(item.getKode());
        holder.nama1.setText(item.getNama());
        holder.satuan1.setText(item.getSatuan());
        holder.harga1.setText(String.valueOf(item.getHarga()));

        try {
            String path = item.getImgPath();
            // Check if the path actually exists and isn't empty
            if (path != null && !path.trim().isEmpty()) {
                Uri imageUri = Uri.parse(path);
                holder.imgView.setImageURI(imageUri);
            } else {
                // Fallback placeholder if no image string exists in database
                holder.imgView.setImageResource(R.drawable.ic_launcher_background);
            }
        } catch (Exception e) {
            Log.e("IMG_LOAD", "Failed To Load: " + e.getMessage());
            holder.imgView.setImageResource(R.drawable.ic_launcher_background);
        }
// --- DUPLICATE CODE REMOVED ---

// Aksi ketika item di list dipencet
        holder.itemView.setOnClickListener(v -> {
            Fragment_update_jual fragment03 = new Fragment_update_jual();
            Bundle bundle = new Bundle();
            bundle.putString("kode", item.getKode());
            bundle.putString("nama", item.getNama());
            bundle.putString("satuan", item.getSatuan());
            bundle.putDouble("harga", item.getHarga());
            bundle.putString("img_path", item.getImgPath());
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
        ImageView imgView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.iv_item);
            kode1 = itemView.findViewById(R.id.textView1);
            nama1 = itemView.findViewById(R.id.textView2);
            satuan1 = itemView.findViewById(R.id.textView3);
            harga1 = itemView.findViewById(R.id.textView4);
        }
    }
}