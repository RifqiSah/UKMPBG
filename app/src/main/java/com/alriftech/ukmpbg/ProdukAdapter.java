package com.alriftech.ukmpbg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ProdukAdapter  extends RecyclerView.Adapter<ProdukHolder>{
    private Context context;
    private ArrayList<Produk> produk;

    public ProdukAdapter(Context context, ArrayList<Produk> produk) {
        this.context = context;
        this.produk = produk;
    }

    @Override
    public ProdukHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_produk, parent, false);
        return new ProdukHolder(view);
    }

    @Override
    public void onBindViewHolder(ProdukHolder holder, int position) {
        Produk produk2 = produk.get(position);
        holder.setDetails(produk2);
    }

    @Override
    public int getItemCount() {
        return produk.size();
    }
}
