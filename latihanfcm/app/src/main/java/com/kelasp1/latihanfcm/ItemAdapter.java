package com.kelasp1.latihanfcm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<ItemModel> itemList;

    public ItemAdapter(List<ItemModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemModel item = itemList.get(position);
        holder.tvNama.setText(item.getNama());
        holder.tvDetail.setText("Kode: " + item.getKode() + " | Satuan: " + item.getSatuan() + " | Harga: " + item.getHarga());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvDetail;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNamaItemRow);
            tvDetail = itemView.findViewById(R.id.tvDetailItemRow);
        }
    }
}