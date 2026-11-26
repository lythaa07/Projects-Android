package com.kelasp1.quizmobile;

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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilAdapter extends RecyclerView.Adapter<ProfilAdapter.ItemViewHolder> {
    private final List<Profil> itemList;
    private final FirebaseFirestore db;
    private final Runnable onRefresh;

    public ProfilAdapter(List<Profil> itemList, FirebaseFirestore db, Runnable onRefresh) {
        this.itemList = itemList;
        this.db = db;
        this.onRefresh = onRefresh;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profil_row, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Profil profil = itemList.get(position);
        holder.text1.setText(profil.getNama());
        holder.text2.setText(profil.getAlamat());
        holder.text3.setText(profil.getTelp());
        holder.text4.setText(String.valueOf(profil.getUmur()));
        holder.text5.setText(profil.getPendidikan());
        holder.text6.setText(profil.getKode());
        holder.text7.setText(profil.getProvinsi());
        holder.text8.setText(profil.getKota());
        holder.text9.setText(profil.getKecamatan());
        holder.text10.setText(profil.getKelurahan());

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.dialog_edit_profil, null);

            EditText etEditNama = dialogView.findViewById(R.id.etEditNama);
            EditText etEditAlamat = dialogView.findViewById(R.id.etEditAlamat);
            EditText etEditTelp = dialogView.findViewById(R.id.etEditTelp);
            EditText etEditUmur = dialogView.findViewById(R.id.etEditUmur);
            EditText etEditPendidikan = dialogView.findViewById(R.id.etEditPendidikan);
            EditText etEditProvinsi = dialogView.findViewById(R.id.etProvinsi);
            EditText etEditKota = dialogView.findViewById(R.id.etEditKota);
            EditText etEditKecamatan = dialogView.findViewById(R.id.etEditKecamatan);
            EditText etEditKelurahan = dialogView.findViewById(R.id.etEditKelurahan);

            etEditNama.setText(profil.getNama());
            etEditAlamat.setText(profil.getAlamat());
            etEditTelp.setText(profil.getTelp());
            etEditUmur.setText(String.valueOf(profil.getUmur()));
            etEditPendidikan.setText(profil.getPendidikan());
            etEditProvinsi.setText(profil.getProvinsi());
            etEditKota.setText(profil.getKota());
            etEditKecamatan.setText(profil.getKecamatan());
            etEditKelurahan.setText(profil.getKelurahan());

            new AlertDialog.Builder(context)
                    .setTitle("Edit Profil")
                    .setView(dialogView)
                    .setPositiveButton("Simpan", (dialog, which) -> {
                        String kode = profil.getKode();
                        if (kode == null || kode.trim().isEmpty()) {
                            Toast.makeText(context, "Kode profil tidak valid", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String namaBaru = etEditNama.getText().toString().trim();
                        String alamatBaru = etEditAlamat.getText().toString().trim();
                        String telpBaru = etEditTelp.getText().toString().trim();
                        String umurStr = etEditUmur.getText().toString().trim();
                        String pendidikanBaru = etEditPendidikan.getText().toString().trim();
                        String provinsiBaru = etEditProvinsi.getText().toString().trim();
                        String kotaBaru = etEditKota.getText().toString().trim();
                        String kecamatanBaru = etEditKecamatan.getText().toString().trim();
                        String kelurahanBaru = etEditKelurahan.getText().toString().trim();

                        if (namaBaru.isEmpty() || alamatBaru.isEmpty() || telpBaru.isEmpty()
                                || umurStr.isEmpty() || pendidikanBaru.isEmpty()) {
                            Toast.makeText(context, "Field wajib diisi", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int umurBaru;
                        try {
                            umurBaru = Integer.parseInt(umurStr);
                        } catch (NumberFormatException e) {
                            Toast.makeText(context, "Umur harus berupa angka", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Profil profilBaru = new Profil(kode, namaBaru, alamatBaru, telpBaru, umurBaru,
                                pendidikanBaru, provinsiBaru, kotaBaru, kecamatanBaru, kelurahanBaru);

                        // 1. Update Firestore pakai set + merge (aman jika dokumen belum ada)
                        db.collection("profil").document(kode)
                                .set(profilBaru, SetOptions.merge())
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(context, "Profil diupdate (Firestore)", Toast.LENGTH_SHORT).show()
                                )
                                .addOnFailureListener(e ->
                                        Toast.makeText(context, "Gagal update Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                );

                        // 2. Update daftar lokal langsung supaya UI responsif
                        itemList.set(position, profilBaru);
                        notifyItemChanged(position);

                        // 3. Update ke Laravel API
                        RetrofitClient.getApiService().updateProfil(kode, profilBaru)
                                .enqueue(new Callback<Profil>() {
                                    @Override
                                    public void onResponse(Call<Profil> call, Response<Profil> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(context, "Data tersimpan di server", Toast.LENGTH_SHORT).show();
                                            if (onRefresh != null) {
                                                onRefresh.run();
                                            }
                                        } else {
                                            Toast.makeText(context, "Gagal update API: " + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Profil> call, Throwable t) {
                                        Toast.makeText(context, "Gagal update API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });

        holder.itemView.setOnLongClickListener(v -> {
            Context context = v.getContext();
            String kode = profil.getKode();
            if (kode == null || kode.trim().isEmpty()) {
                Toast.makeText(context, "Kode profil tidak valid", Toast.LENGTH_SHORT).show();
                return true;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Konfirmasi Hapus")
                    .setMessage("Yakin ingin menghapus item ini?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        // 1. Hapus dari daftar lokal langsung
                        int idx = itemList.indexOf(profil);
                        if (idx >= 0) {
                            itemList.remove(idx);
                            notifyItemRemoved(idx);
                        }

                        // 2. Hapus dari Firestore
                        db.collection("profil").document(kode)
                                .delete()
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(context, "Profil dihapus (Firestore)", Toast.LENGTH_SHORT).show()
                                )
                                .addOnFailureListener(e ->
                                        Toast.makeText(context, "Gagal hapus Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                );

                        // 3. Hapus dari Laravel API
                        RetrofitClient.getApiService().deleteProfil(kode)
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(context, "Profil dihapus di server", Toast.LENGTH_SHORT).show();
                                            if (onRefresh != null) {
                                                onRefresh.run();
                                            }
                                        } else {
                                            Toast.makeText(context, "Gagal hapus di API: " + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(context, "Gagal hapus di API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
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
        TextView text1, text2, text3, text4, text5, text6, text7, text8, text9, text10;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.tvNama);
            text2 = itemView.findViewById(R.id.tvAlamat);
            text3 = itemView.findViewById(R.id.tvTelp);
            text4 = itemView.findViewById(R.id.tvUmur);
            text5 = itemView.findViewById(R.id.tvPendidikan);
            text6 = itemView.findViewById(R.id.tvKode);
            text7 = itemView.findViewById(R.id.tvProvinsi);
            text8 = itemView.findViewById(R.id.tvKota);
            text9 = itemView.findViewById(R.id.tvKecamatan);
            text10 = itemView.findViewById(R.id.tvKelurahan);
        }
    }
}
