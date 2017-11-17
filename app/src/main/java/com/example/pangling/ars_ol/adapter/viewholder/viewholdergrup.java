package com.example.pangling.ars_ol.adapter.viewholder;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pangling.ars_ol.ChatActivity;
import com.example.pangling.ars_ol.MenuActivity;
import com.example.pangling.ars_ol.MenuArisanActivity;
import com.example.pangling.ars_ol.R;
import com.example.pangling.ars_ol.URL.URL;
import com.example.pangling.ars_ol.api.RegisterAPI;
import com.example.pangling.ars_ol.model.Grup;
import com.example.pangling.ars_ol.model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pangling on 11/14/2017.
 */

public class viewholdergrup extends RecyclerView.ViewHolder {
    TextView judul,id;
    CardView kotak;
    Grup grup;

    public viewholdergrup(View itemView) {
        super(itemView);

        judul = (TextView) itemView.findViewById(R.id.tvJudullvaGrup);
        kotak = (CardView) itemView.findViewById(R.id.custome_cardview_arisan);
        id = (TextView) itemView.findViewById(R.id.tvidgrup);
        onClickGrup();
    }

    public void setDatagrup(Grup grup){
        this.grup = grup;
        judul.setText(grup.getGrup().toString());
        id.setText(grup.getId().toString());
    }

    private void onClickGrup(){
        kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                final CharSequence[] dialogitem = {"Hapus Grup","Masuk Grup", "Keluar"};
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                //Toast.makeText(itemView.getContext(),"ini hapus",Toast.LENGTH_SHORT).show();
                                //hapusanggota(id.getText().toString());
                                hapusGrup(id.getText().toString());
                                Intent i = new Intent(itemView.getContext(), MenuArisanActivity.class);
                                itemView.getContext().startActivity(i);
                                break;
                            case 1:
                                Toast.makeText(itemView.getContext(),"Ke Chat",Toast.LENGTH_SHORT).show();
                                if(MenuActivity.namapengguna.equals("Gagah")){
                                    lihatPulsa2();
                                }else{
                                    pindahKechat();
                                }
                                break;
                            case 2:
                                Toast.makeText(itemView.getContext(),"ini keluar",Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }
                });
                builder.create().show();

            }
        });
    }

    public void pindahKechat(){
        Toast.makeText(itemView.getContext(),judul.getText(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(itemView.getContext(), ChatActivity.class);
        intent.putExtra("nama",judul.getText());
        itemView.getContext().startActivity(intent);
    }

    public void hapusGrup(String id){
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
                    Toast.makeText(itemView.getContext(),"Berhasil di Hapus!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(itemView.getContext(),"Gagal di Hapus!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(itemView.getContext(),"Gagal Koneksi!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void lihatPulsa2(){
        // Membuat layout
        LinearLayout layoutinput = new LinearLayout(itemView.getContext());
        layoutinput.setOrientation(LinearLayout.VERTICAL);
        layoutinput.setPadding(50,50,50,50);

        // buat id tersembunyi di alertbuilder
        final TextView txtv = new TextView(itemView.getContext());
        txtv.setText("Sisa pulsa anda : 0");
        txtv.setTextColor(Color.BLACK);
        layoutinput.addView(txtv);

        final TextView txtv2 = new TextView(itemView.getContext());
        txtv2.setText("Min Pulsa : 5000");
        txtv2.setTextColor(Color.BLACK);
        layoutinput.addView(txtv2);

        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
        builder.setTitle("Message");
        builder.setView(layoutinput);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //Membuat tombol positif
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Memunculkan Nama
                //String namaGrup = teks.getText().toString();
                //bikinGrup(namaGrup,MenuActivity.namapengguna,MenuActivity.nomorpenguna,MenuActivity.tokenpengguna);
            }
        });
        builder.show();
    }
}
