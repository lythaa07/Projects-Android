package com.kelasp1.myfragmentt.transaksijual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelasp1.myfragmentt.R; // Import R yang benar

import java.util.List;

public class ItemAdaptertransaksi extends RecyclerView.Adapter<ItemAdaptertransaksi.ViewHolder> {

    private List<Itemtransaksi> itemList;
    private Context context;

    public ItemAdaptertransaksi(List<Itemtransaksi> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public void addItem(Itemtransaksi newItem) {
        boolean found = false;
        for (int i = 0; i < itemList.size(); i++) {
            Itemtransaksi existingItem = itemList.get(i);
            if (existingItem.getKode().equals(newItem.getKode())) {
                int qtyLama = existingItem.getQty();
                int qtyBaru = qtyLama + newItem.getQty();
                existingItem.setQty(qtyBaru);

                double subtotalBaru = qtyBaru * existingItem.getHarga();
                existingItem.setSubtotal(subtotalBaru);

                notifyItemChanged(i);
                found = true;
                break;
            }
        }

        if (!found) {
            itemList.add(newItem);
            notifyItemInserted(itemList.size() - 1);
        }
    }

    public void removeItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            itemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemList.size());
        }
    }

    public List<Itemtransaksi> getItemList() {
        return itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_itemtransaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Itemtransaksi item = itemList.get(position);
        holder.kodeItem.setText(item.getKode());
        holder.namaItem.setText(item.getNama());
        holder.satuanItem.setText(item.getSatuan());
        holder.hargaItem.setText(String.valueOf(item.getHarga()));
        holder.qtyItem.setText(String.valueOf(item.getQty()));
        holder.subtotal.setText(String.valueOf(item.getSubtotal()));

        holder.itemView.setOnClickListener(v -> {

            for (Itemtransaksi it : itemList) {
                if (it.getKode().equalsIgnoreCase(item.getKode())){
                    it.decreaseQty();
                    notifyDataSetChanged();
                }
                if (it.getQty() == 0){
                    removeItem(position);
                }
            }

            // Panggil method hitung grand total di fragment transaksi
            Fragmenttransaksi.hitungGrandTotal();
        });
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView kodeItem, namaItem, satuanItem, hargaItem, qtyItem, subtotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kodeItem = itemView.findViewById(R.id.textViewkode);
            namaItem = itemView.findViewById(R.id.textViewtanggal);
            satuanItem = itemView.findViewById(R.id.textViewgrantotal);
            hargaItem = itemView.findViewById(R.id.textViewharga);
            qtyItem = itemView.findViewById(R.id.textViewqty);
            subtotal = itemView.findViewById(R.id.textViewsubtotal);
        }
    }
}