package com.example.pangling.ars_ol.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pangling.ars_ol.R;
import com.example.pangling.ars_ol.adapter.viewholder.viewholdergrup;
import com.example.pangling.ars_ol.model.Grup;

import java.util.List;

/**
 * Created by Pangling on 11/14/2017.
 */

public class RecycleViewAdapterGrup extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Grup> grups;

    public RecycleViewAdapterGrup(Context context, List<Grup> grups) {
        this.context = context;
        this.grups = grups;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_grup_arisan,parent,false);
        viewholdergrup viewholder = new viewholdergrup(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof viewholdergrup){
            ((viewholdergrup) holder).setDatagrup(grups.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return grups == null ? 0 : grups.size();
    }
}
