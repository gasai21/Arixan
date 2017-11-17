package com.example.pangling.ars_ol.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.pangling.ars_ol.ChatActivity;
import com.example.pangling.ars_ol.model.ModelAnggota;

/**
 * Created by Pangling on 11/17/2017.
 */

public class viewholdergetanggota extends RecyclerView.ViewHolder {
    ModelAnggota data;
    public viewholdergetanggota(View itemView) {
        super(itemView);
    }

    public void setUpData(ModelAnggota data){
        this.data = data;
        ChatActivity.datalist.add(data.getNama());
    }
}
