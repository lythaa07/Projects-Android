package com.kelasp1.myfragmentt.transaksijual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kelasp1.myfragmentt.R;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {

    private List<Transaksi> transaksiList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(Transaksi transaksi);
    }

    public TransaksiAdapter(List<Transaksi> transaksiList, Context context, OnItemClickListener onItemClickListener) {
        this.transaksiList = transaksiList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public List<Transaksi> getTransaksiList() {
        return transaksiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Pastikan R.layout.layout_transaksi sudah ada di res/layout/
        View view = LayoutInflater.from(context).inflate(R.layout.layout_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaksi transaksi = transaksiList.get(position);

        holder.kode.setText(transaksi.getKodetr());
        holder.tanggal.setText(transaksi.getTanggal());
        holder.grandtotal.setText(String.valueOf(transaksi.getGrandtotal()));

        holder.itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(transaksi);
        });
    }

    @Override
    public int getItemCount() {
        return transaksiList != null ? transaksiList.size() : 0;
    }

    // Menggunakan static agar lebih efisien memori
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView kode, tanggal, grandtotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Pastikan ID sama persis dengan yang ada di layout_transaksi.xml
            kode = itemView.findViewById(R.id.textViewkode);
            tanggal = itemView.findViewById(R.id.textViewtanggal);
            grandtotal = itemView.findViewById(R.id.textViewgrantotal);
        }
    }
}