package com.example.pangling.ars_ol.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pangling.ars_ol.R;
import com.example.pangling.ars_ol.model.ChatModel;

/**
 * Created by Pangling on 11/14/2017.
 */

public class viewholderchatkiri extends RecyclerView.ViewHolder {
    TextView nama,pesan;
    public viewholderchatkiri(View itemView) {
        super(itemView);

        nama = (TextView) itemView.findViewById(R.id.chatkiri_custom_nama);
        pesan = (TextView) itemView.findViewById(R.id.chatkiri_custom);
    }

    public void setUI(ChatModel model){
        nama.setText(model.getPengirim());
        pesan.setText(model.getPesan());
    }
}
