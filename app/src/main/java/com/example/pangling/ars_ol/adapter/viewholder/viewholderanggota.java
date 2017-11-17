package com.example.pangling.ars_ol.adapter.viewholder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pangling.ars_ol.R;
import com.example.pangling.ars_ol.URL.URL;
import com.example.pangling.ars_ol.ViewAnggotaActivity;
import com.example.pangling.ars_ol.api.RegisterAPI;
import com.example.pangling.ars_ol.model.ModelAnggota;
import com.example.pangling.ars_ol.model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pangling on 11/15/2017.
 */

public class viewholderanggota extends RecyclerView.ViewHolder {

    TextView nama,nomor,id;
    ModelAnggota data;
    CardView cd;
    public viewholderanggota(View itemView) {
        super(itemView);
        nama = (TextView) itemView.findViewById(R.id.tvCustomeNama);
        nomor = (TextView) itemView.findViewById(R.id.tvCustomeNomor);
        cd =(CardView) itemView.findViewById(R.id.cdanggota);
        id = (TextView) itemView.findViewById(R.id.tvid);
        ngeclick();
    }

    public void setUpData(ModelAnggota data){
        this.data = data;
        nama.setText(data.getNama());
        nomor.setText(data.getNomor());
        id.setText(data.getId());
        ViewAnggotaActivity.dataanggota.add(data.getNama());
    }

    public void ngeclick(){
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                final CharSequence[] dialogitem = {"Hapus Anggota", "Keluar"};
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                //Toast.makeText(itemView.getContext(),"ini hapus",Toast.LENGTH_SHORT).show();
                                hapusanggota(id.getText().toString());
                                break;
                            case 1:
                                Toast.makeText(itemView.getContext(),"ini keluar",Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    public void hapusanggota(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.deleteAnggota(id);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if(value.equals("1")){
                    Toast.makeText(itemView.getContext(),"Data berhasil dihapus!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(itemView.getContext(), ViewAnggotaActivity.class);
                    itemView.getContext().startActivity(i);
                }else{
                    Toast.makeText(itemView.getContext(),"Data gagal dihapus!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(itemView.getContext(),"Gagal Koneksi!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
