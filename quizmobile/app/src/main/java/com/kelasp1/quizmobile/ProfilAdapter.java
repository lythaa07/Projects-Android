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

import com.google.firebase.firestore.CollectionReference;

import java.util.List;

public class ProfilAdapter extends RecyclerView.Adapter<ProfilAdapter.ProfilViewHolder> {

    private List<Profil> profilList;
    private CollectionReference dbRef;

    public ProfilAdapter(List<Profil> profilList, CollectionReference dbRef) {
        this.profilList = profilList;
        this.dbRef = dbRef;
    }

    @NonNull
    @Override
    public ProfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profil_row, parent, false);
        return new ProfilViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilViewHolder holder, int position) {
        Profil profil = profilList.get(position);

        // Mengisi data murni tanpa teks tambahan
        holder.tvKode.setText(profil.getKode());
        holder.tvNama.setText(profil.getNama());
        holder.tvUmur.setText(profil.getUmur());
        holder.tvAlamat.setText(profil.getAlamat());
        holder.tvTelp.setText(profil.getTelp());
        holder.tvPendidikan.setText(profil.getPendidikan());
        holder.tvProvinsi.setText(profil.getProvinsi());
        holder.tvKota.setText(profil.getKota());
        holder.tvKecamatan.setText(profil.getKecamatan());
        holder.tvKelurahan.setText(profil.getKelurahan());

        // Klik untuk Edit Data
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
            EditText etEditKeluruhan = dialogView.findViewById(R.id.etEditKelurahan);

            etEditNama.setText(profil.getNama());
            etEditAlamat.setText(profil.getAlamat());
            etEditTelp.setText(profil.getTelp());
            etEditUmur.setText(profil.getUmur());
            etEditPendidikan.setText(profil.getPendidikan());
            etEditProvinsi.setText(profil.getProvinsi());
            etEditKota.setText(profil.getKota());
            etEditKecamatan.setText(profil.getKecamatan());
            etEditKeluruhan.setText(profil.getKelurahan());


            new AlertDialog.Builder(context)
                    .setTitle("Edit Data Profil")
                    .setView(dialogView)
                    .setPositiveButton("Update", (dialog, which) -> {
                        profil.setNama(etEditNama.getText().toString());
                        profil.setAlamat(etEditAlamat.getText().toString());
                        profil.setTelp(etEditTelp.getText().toString());
                        profil.setUmur(etEditUmur.getText().toString());
                        profil.setPendidikan(etEditPendidikan.getText().toString());
                        profil.setProvinsi(etEditProvinsi.getText().toString());
                        profil.setKota(etEditKota.getText().toString());
                        profil.setKecamatan(etEditKecamatan.getText().toString());
                        profil.setKelurahan(etEditKeluruhan.getText().toString());


                        dbRef.document(profil.getKode()).set(profil);
                        Toast.makeText(context, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });

        // Long Click untuk Hapus Data
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Konfirmasi Hapus")
                    .setMessage("Yakin ingin menghapus data " + profil.getNama() + "?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        dbRef.document(profil.getKode()).delete();
                        Toast.makeText(v.getContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return profilList.size();
    }

    public static class ProfilViewHolder extends RecyclerView.ViewHolder {
        TextView tvKode, tvNama, tvUmur, tvAlamat, tvTelp, tvPendidikan, tvProvinsi, tvKota, tvKecamatan, tvKelurahan;

        public ProfilViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKode = itemView.findViewById(R.id.tvKode);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvUmur = itemView.findViewById(R.id.tvUmur);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvTelp = itemView.findViewById(R.id.tvTelp);
            tvPendidikan = itemView.findViewById(R.id.tvPendidikan);
            tvProvinsi =itemView.findViewById(R.id.tvProvinsi);
            tvKota = itemView.findViewById(R.id.tvKota);
            tvKecamatan = itemView.findViewById(R.id.tvKecamatan);
            tvKelurahan = itemView.findViewById(R.id.tvKelurahan);
        }
    }
}