package com.example.pangling.ars_ol.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pangling.ars_ol.R;
import com.example.pangling.ars_ol.adapter.viewholder.viewholderchatkanan;
import com.example.pangling.ars_ol.adapter.viewholder.viewholderchatkiri;
import com.example.pangling.ars_ol.constant.Constant;
import com.example.pangling.ars_ol.model.ChatModel;

import java.util.ArrayList;

/**
 * Created by Pangling on 11/14/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<ChatModel> data;

    public ChatAdapter(Context context, ArrayList<ChatModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        ChatModel ca = data.get(position);
        if(ca.getIsBot()==0){
            return Constant.TAG_RIGHT;
        }else{
            return Constant.TAG_LEFT;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(viewType == Constant.TAG_LEFT){
            v = LayoutInflater.from(context).inflate(R.layout.custom_chatkiri,null);
            return new viewholderchatkiri(v);
        }else{
            v = LayoutInflater.from(context).inflate(R.layout.custom_chatkanan,null);
            return new viewholderchatkanan(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof viewholderchatkanan){
            ChatModel model = data.get(position);
            ((viewholderchatkanan) holder).setUI(model);
        }else if(holder instanceof viewholderchatkiri){
            ChatModel model = data.get(position);
            ((viewholderchatkiri) holder).setUI(model);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
