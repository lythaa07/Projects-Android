package com.kelasp1.latihanfirestore03;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.CollectionReference;
import java.util.List;
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;
    private CollectionReference dbRef;
    public ItemAdapter(List<Item> itemList, CollectionReference dbRef) {
        this.itemList = itemList;
        this.dbRef = dbRef;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.tvNama.setText(item.getNama());
        holder.tvDetail.setText("Kode: " + item.getKode() + " | Satuan: " + item.getSatuan() + " | Harga: " + item.getHarga());
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.dialog_edit_item, null);
            EditText etEditNama = dialogView.findViewById(R.id.etEditNama);
            EditText etEditSatuan = dialogView.findViewById(R.id.etEditSatuan);
            EditText etEditHarga = dialogView.findViewById(R.id.etEditHarga);
            etEditNama.setText(item.getNama());
            etEditSatuan.setText(item.getSatuan());
            etEditHarga.setText(String.valueOf(item.getHarga()));
            new AlertDialog.Builder(context)
                    .setTitle("Edit Item")
                    .setView(dialogView)
                    .setPositiveButton("Update", (dialog, which) -> {
                        item.setNama(etEditNama.getText().toString());
                        item.setSatuan(etEditSatuan.getText().toString());
                        item.setHarga(Integer.parseInt(etEditHarga.getText().toString()));
                        dbRef.document(item.getKode()).set(item);
                        Toast.makeText(context, "Item berhasil diupdate", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Konfirmasi Hapus")
                    .setMessage("Yakin ingin menghapus item " + item.getNama() + "?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        dbRef.document(item.getKode()).delete();
                        Toast.makeText(v.getContext(), "Item dihapus", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
            return true;
        });
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvDetail;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvDetail = itemView.findViewById(R.id.tvDetail);
        }
    }
}