package com.kelasp1.myfragmentt.transaksijual;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kelasp1.myfragmentt.R;
import java.util.List;

public class AdapterDetail extends RecyclerView.Adapter<AdapterDetail.ViewHolder> {
    private List<DetailTransaksi> list;

    public AdapterDetail(List<DetailTransaksi> list) { this.list = list; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detail_transaksi, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailTransaksi item = list.get(position);

        holder.kode.setText(item.getKode());
        holder.nama.setText(item.getNama());
        holder.satuan.setText(item.getSatuan());
        holder.qty.setText(String.valueOf(item.getQty()));
        holder.subtotal.setText(String.valueOf(item.getSubtotal()));
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView kode, nama, satuan, qty, subtotal;
        public ViewHolder(View v) {
            super(v);
            kode = v.findViewById(R.id.tv_kode);
            nama = v.findViewById(R.id.tv_nama);
            satuan = v.findViewById(R.id.tv_satuan);
            qty = v.findViewById(R.id.tv_qty);
            subtotal = v.findViewById(R.id.tv_subtotal);
        }
    }
}