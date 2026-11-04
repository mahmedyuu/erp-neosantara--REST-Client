package com.ahmadyusuf.uts_ahmadyusufn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ahmadyusuf.uts_ahmadyusufn.Model.Barang;

import java.util.List;
import java.util.Locale;

public class BarangAdapter extends ArrayAdapter<Barang> {

    public BarangAdapter(Context context, List<Barang> daftarBarang) {
        super(context, 0, daftarBarang);
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(
                            android.R.layout.simple_list_item_2,
                            parent,
                            false
                    );
        }

        Barang barang = getItem(position);

        TextView text1 = convertView.findViewById(
                android.R.id.text1
        );

        TextView text2 = convertView.findViewById(
                android.R.id.text2
        );

        if (barang != null) {
            text1.setText(barang.getNama_barang());

            String harga = barang.getHarga() == null
                    ? "0"
                    : barang.getHarga();

            text2.setText(String.format(
                    Locale.getDefault(),
                    "Kategori: %s | Stok: %d | Harga: Rp%s",
                    barang.getKategori(),
                    barang.getStok(),
                    harga
            ));
        }

        return convertView;
    }
}
