package com.alriftech.ukmpbg;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class LowonganHolder extends RecyclerView.ViewHolder  {
    private TextView txtJudul, txtDeskripsi, txtGaji;

    public LowonganHolder(View itemView) {
        super(itemView);

        this.txtJudul       = itemView.findViewById(R.id.txt_list_lowongan_judul);
        this.txtDeskripsi   = itemView.findViewById(R.id.txt_list_lowongan_deskripsi);
        this.txtGaji        = itemView.findViewById(R.id.txt_list_lowongan_gaji);
    }

    public void setDetails(final Lowongan lowongan) {
        txtJudul.setText(lowongan.getJudul());
        txtDeskripsi.setText(lowongan.getDeskripsi());
        txtGaji.setText(String.format("Rp. %,.2f", Float.valueOf(lowongan.getGaji())));
    }
}
