package com.alriftech.ukmpbg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class UKMHolder extends RecyclerView.ViewHolder {
    private TextView txtNama, txtAlamat, txtWaktu;

    public UKMHolder(View itemView) {
        super(itemView);

        this.txtNama        = itemView.findViewById(R.id.txt_list_ukm_nama);
        this.txtAlamat      = itemView.findViewById(R.id.txt_list_ukm_alamat);
        this.txtWaktu       = itemView.findViewById(R.id.txt_list_waktu);
    }

    public void setDetails(final UKM ukm) {
        txtNama.setText(ukm.getNama_ukm());
        txtAlamat.setText(ukm.getAlamat());
        txtWaktu.setText(ukm.getJam_buka() + " sampai " + ukm.getJam_tutup());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = itemView.getRootView().getContext();

                c.startActivity(new Intent(c, DetailUKMActivity.class).putExtra("id_ukm", ukm.getId_ukm()));
            }
        });
    }
}