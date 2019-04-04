package com.alriftech.ukmpbg;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ProdukHolder extends RecyclerView.ViewHolder {
    private TextView txtNama, txtDeskripsi, txtHarga;

    public ProdukHolder(View itemView) {
        super(itemView);

        this.txtNama        = itemView.findViewById(R.id.txt_list_produk_nama);
        this.txtDeskripsi   = itemView.findViewById(R.id.txt_list_produk_deskripsi);
        this.txtHarga       = itemView.findViewById(R.id.txt_list_produk_harga);
    }

    public void setDetails(final Produk produk) {
        txtNama.setText(produk.getNama_produk());
        txtDeskripsi.setText(produk.getDeskripsi());
        txtHarga.setText(String.format("Rp. %,.2f", Float.valueOf(produk.getHarga())));
    }
}
