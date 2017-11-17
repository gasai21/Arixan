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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pangling.ars_ol.URL.URL;
import com.example.pangling.ars_ol.adapter.RecycleViewAdapterAnggota;
import com.example.pangling.ars_ol.api.RegisterAPI;
import com.example.pangling.ars_ol.model.ModelAnggota;
import com.example.pangling.ars_ol.model.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAnggotaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ModelAnggota> data = new ArrayList<>();
    RecycleViewAdapterAnggota adapter;
    ProgressDialog progressDialog;
    Context context = this;
    Button tambahAkun;
    Button kocok;
    public static List<String> dataanggota = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anggota);
        getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.rvanggota);
        tambahAkun = (Button) findViewById(R.id.btnTambahAnggota);
        kocok = (Button) findViewById(R.id.btnKocok);

        kocok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                String rand = dataanggota.get(random.nextInt(dataanggota.size()));
                Toast.makeText(getApplicationContext(),rand,Toast.LENGTH_SHORT).show();
            }
        });

        //setting progressdialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        tambahAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuat layout
                LinearLayout layoutinput = new LinearLayout(context);
                layoutinput.setOrientation(LinearLayout.VERTICAL);
                layoutinput.setPadding(50,50,50,50);

                // buat id tersembunyi di alertbuilder
                final TextView txtv = new TextView(context);
                txtv.setText("Nama Anggota");
                txtv.setTextColor(Color.BLACK);
                layoutinput.addView(txtv);

                final EditText teks = new EditText(context);
                layoutinput.addView(teks);

                final TextView txtv2 = new TextView(context);
                txtv2.setText("Nomor Telepon");
                txtv2.setTextColor(Color.BLACK);
                layoutinput.addView(txtv2);

                final EditText teks2 = new EditText(context);
                layoutinput.addView(teks2);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Menambahkan Anggota");
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
                        String namaanggota = teks.getText().toString();
                        String nomortelepon = teks2.getText().toString();
                        simpananggota(namaanggota,nomortelepon,ChatActivity.namagruppp);
                    }
                });
                builder.show();
            }
        });

        setadapteranggota();
        tampilanggota(ChatActivity.namagruppp);
    }

    public void setadapteranggota(){
        adapter = new RecycleViewAdapterAnggota(data,ViewAnggotaActivity.this);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    public void tampilanggota(String nilai){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.tampilAnggota(nilai);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if(value.equals("1")){
                    data = response.body().getAmbilA();
                    adapter = new RecycleViewAdapterAnggota(data,ViewAnggotaActivity.this);
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(getApplicationContext(),"Gagal Menampilkan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal Koneksi",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void simpananggota(String nama, String nomor, String grup){
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.tambahAnggota(nama,grup,nomor);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                progressDialog.dismiss();
                String value = response.body().getValue();
                if(value.equals("1")){
                    Toast.makeText(getApplicationContext(),"Berhasil di Tambah!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context,ViewAnggotaActivity.class);
                    finish();
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Gagal di Tambah!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Koneksi Gagal!",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
