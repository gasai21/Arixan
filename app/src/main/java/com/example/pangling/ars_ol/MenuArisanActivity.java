package com.example.pangling.ars_ol;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pangling.ars_ol.URL.URL;
import com.example.pangling.ars_ol.adapter.RecycleViewAdapterGrup;
import com.example.pangling.ars_ol.api.RegisterAPI;
import com.example.pangling.ars_ol.model.Grup;
import com.example.pangling.ars_ol.model.Value;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuArisanActivity extends AppCompatActivity {

    Button tambah;
    Context context = this;
    ProgressDialog progressDialog;
    List<Grup> grups;
    RecycleViewAdapterGrup adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_arisan);
        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.review);

        //setting progressdialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        tambah = (Button) findViewById(R.id.btnTambahArisan);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuat layout
                LinearLayout layoutinput = new LinearLayout(context);
                layoutinput.setOrientation(LinearLayout.VERTICAL);
                layoutinput.setPadding(50,50,50,50);

                // buat id tersembunyi di alertbuilder
                final TextView txtv = new TextView(context);
                txtv.setText("Nama Grup");
                txtv.setTextColor(Color.BLACK);
                layoutinput.addView(txtv);

                final EditText teks = new EditText(context);
                layoutinput.addView(teks);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Membuat Grup Arisan");
                builder.setView(layoutinput);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                //Membuat tombol positif
                builder.setPositiveButton("Buat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Memunculkan Nama
                        String namaGrup = teks.getText().toString();
                        bikinGrup(namaGrup,MenuActivity.namapengguna,MenuActivity.nomorpenguna,MenuActivity.tokenpengguna);
                    }
                });
                builder.show();
            }

        });

        setAdapterr();
        tampilgrup(MenuActivity.namapengguna);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilgrup(MenuActivity.namapengguna);
    }

    public void bikinGrup(String namagrup, String namapenggunaa,String nmr,String token){
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.bikinG(namagrup,namapenggunaa,nmr,token);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                progressDialog.dismiss();
                String value = response.body().getValue();
                String messege = response.body().getMessage();
                if(value.equals("1")){
                    Toast.makeText(getApplicationContext(),messege,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MenuArisanActivity.this,MenuArisanActivity.class);
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),messege,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Gagal Koneksi",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void tampilgrup(String nilai){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.tampilGrup(nilai);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if(value.equals("1")){
                    grups = response.body().getAmbilH();
                    adapter = new RecycleViewAdapterGrup(MenuArisanActivity.this,grups);
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal Koneksi!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setAdapterr(){
        adapter = new RecycleViewAdapterGrup(MenuArisanActivity.this, grups);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
}
