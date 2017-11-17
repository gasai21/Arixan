package com.example.pangling.ars_ol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pangling.ars_ol.URL.URL;
import com.example.pangling.ars_ol.adapter.ChatAdapter;
import com.example.pangling.ars_ol.adapter.RecycleViewGetAnggota;
import com.example.pangling.ars_ol.api.RegisterAPI;
import com.example.pangling.ars_ol.model.ChatModel;
import com.example.pangling.ars_ol.model.ModelAnggota;
import com.example.pangling.ars_ol.model.Value;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    TextView judul;
    Intent i = null;
    RecyclerView recyclerView;
    EditText pesan;
    ArrayList<ChatModel> data = new ArrayList<>();
    ChatAdapter adapter;
    ImageView send;
    String pushpesan = "Arisannya Udah mau mulai Nih!";
    String pushtitle = "BOT";
    Context context = this;

    List<ModelAnggota> data1 = new ArrayList<>();
    RecycleViewGetAnggota adapter1;

    public static String namagruppp;
    public static List<String> datalist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        //inisialisasi
        judul = (TextView) findViewById(R.id.titleChat);
        recyclerView = (RecyclerView) findViewById(R.id.rvChat);
        pesan = (EditText) findViewById(R.id.txtKirimChat);
        send = (ImageView) findViewById(R.id.btnKirimChat);
        i = getIntent();
        namagruppp = i.getStringExtra("nama");
        judul.setText(i.getStringExtra("nama"));

        //pemanggilan method yg di buat
        setAdapter();
        addChat();
        etChatClicked();
    }

    private void setAdapter(){
        adapter1 = new RecycleViewGetAnggota(data1,ChatActivity.this);
        adapter = new ChatAdapter(this, data);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void etChatClicked(){
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesan.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(data.size() - 1);
                    }
                }, 100);
            }
        });
    }

    private void addChat(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String isi = pesan.getText().toString();
            data.add(new ChatModel(data.size() + 1, 0, MenuActivity.namapengguna, isi));
            if (isi.equals("/Tampil Anggota Manual")) {
                data.add(new ChatModel(data.size() + 1, 1, "BOT", "Siap di laksanakan!"));
                Intent i = new Intent(ChatActivity.this, ViewAnggotaActivity.class);
                startActivity(i);
            } else if (isi.equals("/notif")) {
                data.add(new ChatModel(data.size() + 1, 1, "BOT", "Siap di laksanakan!"));
                notif(namagruppp, pushtitle, pushpesan);
            } else if (isi.equals("/Mulai Arisan")) {
                data.add(new ChatModel(data.size() + 1, 1, "BOT", "Siap di laksanakan!"));
                lihatPulsa();
                ambilhasilArisan();
            } else if (isi.equals("/Check Pulsa")) {
                data.add(new ChatModel(data.size() + 1, 1, "BOT", "Siap di laksanakan!"));
                lihatPulsa();
            } else if (isi.equals("/schedule")) {
                data.add(new ChatModel(data.size() + 1, 1, "BOT", "Siap di laksanakan!"));
                schedule();
            } else {
                //data.add(new ChatModel(data.size()+1,1,"BOT","Masih Dalam Pengembangan"));
            }
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(data.size() - 1);

            pesan.setText("");
            pesan.requestFocus();

            }
        });
    }

    public void notif(String grup, String title, String message){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.pushNotif(grup,title,message);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                Toast.makeText(getApplicationContext(),"Lagi ngejalanin",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal Koneksi!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ambilhasilArisan(){
        Handler a = new Handler();
        a.postDelayed(new Runnable() {
            @Override
            public void run() {
                data.add(new ChatModel(data.size()+1,1,"BOT","3"));
                ambilhasilArisan2();
            }
        },2000L);
    }

    public void ambilhasilArisan2(){
        Handler a = new Handler();
        a.postDelayed(new Runnable() {
            @Override
            public void run() {
                data.add(new ChatModel(data.size()+1,1,"BOT","2"));
                ambilhasilArisan3();
            }
        },2000L);
    }

    public void ambilhasilArisan3(){
        Handler a = new Handler();
        a.postDelayed(new Runnable() {
            @Override
            public void run() {
                data.add(new ChatModel(data.size()+1,1,"BOT","1"));
                ambilhasilArisanY();
            }
        },2000L);
    }

    public void ambilhasilArisanY(){
        Handler a = new Handler();
        a.postDelayed(new Runnable() {
            @Override
            public void run() {
                data.add(new ChatModel(data.size()+1,1,"BOT","Selamat kepada "+MenuActivity.namapengguna+", Arisannya sudah keluar"));
            }
        },2000L);
    }

    public void lihatPulsa(){
        // Membuat layout
        LinearLayout layoutinput = new LinearLayout(context);
        layoutinput.setOrientation(LinearLayout.VERTICAL);
        layoutinput.setPadding(50,50,50,50);

        // buat id tersembunyi di alertbuilder
        final TextView txtv = new TextView(context);
        txtv.setText("Sisa pulsa anda : 25000");
        txtv.setTextColor(Color.BLACK);
        layoutinput.addView(txtv);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    public void lihatPulsa2(){
        // Membuat layout
        LinearLayout layoutinput = new LinearLayout(context);
        layoutinput.setOrientation(LinearLayout.VERTICAL);
        layoutinput.setPadding(50,50,50,50);

        // buat id tersembunyi di alertbuilder
        final TextView txtv = new TextView(context);
        txtv.setText("Sisa pulsa anda : 0");
        txtv.setTextColor(Color.BLACK);
        layoutinput.addView(txtv);

        final TextView txtv2 = new TextView(context);
        txtv2.setText("Min Pulsa : 5000");
        txtv2.setTextColor(Color.BLACK);
        layoutinput.addView(txtv2);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    public void schedule(){
        // Membuat layout
        LinearLayout layoutinput = new LinearLayout(context);
        layoutinput.setOrientation(LinearLayout.VERTICAL);
        layoutinput.setPadding(50,50,50,50);

        // buat id tersembunyi di alertbuilder
        final TextView txtv = new TextView(context);
        txtv.setText("Tanggal Recomendasi Jadwal pengocokan");
        txtv.setTextColor(Color.BLACK);
        layoutinput.addView(txtv);

        final EditText teks = new EditText(context);
        teks.setText("24 November 2017");
        layoutinput.addView(teks);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Schedule");
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
                Toast.makeText(getApplicationContext(),"Data di Simpan",Toast.LENGTH_SHORT).show();
                // Memunculkan Nama
                //String namaGrup = teks.getText().toString();
                //bikinGrup(namaGrup,MenuActivity.namapengguna,MenuActivity.nomorpenguna,MenuActivity.tokenpengguna);
            }
        });
        builder.show();
    }
}
