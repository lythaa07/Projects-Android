package com.kelasp1.pertemuan07;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kelasp1.pertemuan07.databinding.ItemHistoryBinding;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<Order> orderList = new ArrayList<>();
    // [TAMBAH] Cache produk (id -> Product) untuk menampilkan nama & harga
    // ketimbang productId yang mentah.
    private Map<String, Product> productMap = new HashMap<>();

    public void setOrderList(List<Order> newList) {
        this.orderList = newList;
        notifyDataSetChanged();
    }

    public void setProductMap(Map<String, Product> newMap) {
        this.productMap = newMap != null ? newMap : new HashMap<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Order order = orderList.get(position);
        Product product = productMap.get(order.getProductId());

        holder.binding.tvOrderId.setText("Order ID: " + order.getId());
        holder.binding.tvQuantity.setText("Jumlah Item: " + order.getQuantity());

        // [TAMBAH] Tampilkan nama produk, bukan productId mentah.
        // Jika produk sudah dihapus dari koleksi, fallback ke ID-nya.
        if (product != null && product.getName() != null) {
            holder.binding.tvProductName.setText(product.getName());
            holder.binding.tvPrice.setVisibility(View.VISIBLE);
            holder.binding.tvTotal.setVisibility(View.VISIBLE);
            holder.binding.tvPrice.setText("Harga: " + formatRupiah(product.getPrice()));
            holder.binding.tvTotal.setText("Total: " + formatRupiah(product.getPrice() * order.getQuantity()));
        } else {
            holder.binding.tvProductName.setText("Produk: " + order.getProductId());
            holder.binding.tvPrice.setVisibility(View.GONE);
            holder.binding.tvTotal.setVisibility(View.GONE);
        }

        if (order.getTimestamp() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
            holder.binding.tvTimestamp.setText("Waktu Transaksi: " + sdf.format(order.getTimestamp()));
        } else {
            holder.binding.tvTimestamp.setText("Waktu Transaksi: Memproses...");
        }
    }

    // Format angka rupiah tanpa desimal, mis. Rp 15000 -> Rp 15.000
    private String formatRupiah(double value) {
        NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("id", "ID"));
        return "Rp " + nf.format(Math.round(value));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        final ItemHistoryBinding binding;

        HistoryViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
