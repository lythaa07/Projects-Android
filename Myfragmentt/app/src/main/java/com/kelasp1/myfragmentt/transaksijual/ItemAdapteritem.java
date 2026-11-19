package com.kelasp1.myfragmentt.transaksijual;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
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

public class ItemAdapteritem extends RecyclerView.Adapter<ItemAdapteritem.ViewHolder>{

    private List<Item> itemList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public ItemAdapteritem(List<Item> itemList, Context context, OnItemClickListener listener) {
        this.itemList = itemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemAdapteritem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_itemitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapteritem.ViewHolder holder, int position) {

        Item item = itemList.get(position);
        holder.namaItem.setText(item.getNama());
        holder.hargaItem.setText(item.getHarga().toString());


        try {
            String path = item.getImage();
            // Check if the path actually exists and isn't empty
            if (path != null && !path.trim().isEmpty()) {
                Uri imageUri = Uri.parse(path);
                holder.imgItem.setImageURI(imageUri);
            } else {
                // Fallback placeholder if no image string exists in database
                holder.imgItem.setImageResource(R.drawable.ic_launcher_background);
            }
        } catch (Exception e) {
            Log.e("IMG_LOAD", "Failed To Load: " + e.getMessage());
            holder.imgItem.setImageResource(R.drawable.ic_launcher_background);
        }


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  namaItem, hargaItem;
        ImageView imgItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaItem = itemView.findViewById(R.id.textViewtanggal);
            hargaItem = itemView.findViewById(R.id.textViewharga);
            imgItem = itemView.findViewById(R.id.imageViewitem);

        }
    }
}

