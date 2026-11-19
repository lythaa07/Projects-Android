package com.kelasp1.myfragmentt.transaksijual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kelasp1.myfragmentt.R;
import com.kelasp1.myfragmentt.database.DBHelper;
import java.util.List;

public class FragmentDetailTransaksi extends Fragment {
    private String kodeTransaksi;
    private Transaksi curTransaksi;
    private TextView kode, tgl, grandTotal;
    private Button close;

    public void setCurTransaksi(Transaksi curTransaksi) {
        this.curTransaksi = curTransaksi;
    }

    // Gunakan static factory method untuk mengirim kode transaksi
    public static FragmentDetailTransaksi newInstance(Transaksi transaksi) {
        FragmentDetailTransaksi fragment = new FragmentDetailTransaksi();
        Bundle args = new Bundle();
        args.putString("KODE_TR", transaksi.getKodetr());
        fragment.setArguments(args);
        fragment.setCurTransaksi(transaksi);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_transaksi, container, false);
        kode = v.findViewById(R.id.tv_detail_kode);
        tgl = v.findViewById(R.id.tv_detail_tanggal);
        grandTotal = v.findViewById(R.id.tv_detail_total);
        close = v.findViewById(R.id.btn_close_detail);

        kode.setText(curTransaksi.getKodetr());
        tgl.setText(curTransaksi.getTanggal());
        grandTotal.setText("Rp " + curTransaksi.getGrandtotal());
        if (getArguments() != null) kodeTransaksi = getArguments().getString("KODE_TR");

        RecyclerView rv = v.findViewById(R.id.rv_detail);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        DBHelper db = new DBHelper(getContext());
        List<DetailTransaksi> data = db.getDetailTransaksi(kodeTransaksi);
        rv.setAdapter(new AdapterDetail(data));

        close.setOnClickListener(v1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragmenttransaksilist())
                    .commit();
        });
        return v;
    }
}