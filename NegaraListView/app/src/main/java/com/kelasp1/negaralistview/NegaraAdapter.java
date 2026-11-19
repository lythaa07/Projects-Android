package com.kelasp1.negaralistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelasp1.negaralistview.R;

public class NegaraAdapter extends BaseAdapter {
    Context context;
    String[] negaralist;
    int[] benderalist;
    LayoutInflater inflater;

    public NegaraAdapter(Context context, String[] negaralist, int[] benderalist) {
        this.context = context;
        this.negaralist = negaralist;
        this.benderalist = benderalist;
        this.inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return negaralist.length;
    }

    @Override
    public Object getItem(int position) {
        return negaralist[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_item, null);
        }

        ImageView img = convertView.findViewById(R.id.imgBendera);
        TextView txt = convertView.findViewById(R.id.txtNamaNegara);

        txt.setText(negaralist[position]);
        img.setImageResource(benderalist[position]);

        return convertView;
    }
}