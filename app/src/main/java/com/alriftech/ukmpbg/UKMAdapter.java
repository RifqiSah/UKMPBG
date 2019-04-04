package com.alriftech.ukmpbg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class UKMAdapter extends RecyclerView.Adapter<UKMHolder>{
    private Context context;
    private ArrayList<UKM> ukm;

    public UKMAdapter(Context context, ArrayList<UKM> ukm) {
        this.context = context;
        this.ukm = ukm;
    }

    @Override
    public UKMHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_ukm, parent, false);
        return new UKMHolder(view);
    }

    @Override
    public void onBindViewHolder(UKMHolder holder, int position) {
        UKM ukm2 = ukm.get(position);
        holder.setDetails(ukm2);
    }

    @Override
    public int getItemCount() {
        return ukm.size();
    }
}
