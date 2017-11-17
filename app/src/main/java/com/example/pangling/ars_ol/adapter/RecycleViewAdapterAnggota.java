package com.example.pangling.ars_ol.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pangling.ars_ol.R;
import com.example.pangling.ars_ol.adapter.viewholder.viewholderanggota;
import com.example.pangling.ars_ol.model.ModelAnggota;

import java.util.List;

/**
 * Created by Pangling on 11/15/2017.
 */

public class RecycleViewAdapterAnggota extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ModelAnggota> data;
    Context context;

    public RecycleViewAdapterAnggota(List<ModelAnggota> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_list_anggota,null);
        viewholderanggota grup = new viewholderanggota(v);
        return grup;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof viewholderanggota){
            ((viewholderanggota) holder).setUpData(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
