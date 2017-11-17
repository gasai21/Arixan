package com.example.pangling.ars_ol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    Button btnMenu,btnLogout;
    Intent intent=null;
    TextView nama,notelp;
    public static String namapengguna;
    public static String nomorpenguna;
    public static String tokenpengguna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        //inisialisasi
        btnMenu = (Button) findViewById(R.id.btnMenuArisan);
        btnLogout = (Button) findViewById(R.id.btnMenuLogout);
        nama = (TextView) findViewById(R.id.tvNamaMenu);
        notelp = (TextView) findViewById(R.id.tvNoTelp);

        //ganti bagian atas
        Intent i = getIntent();
        namapengguna = i.getStringExtra("nama");
        nomorpenguna = i.getStringExtra("nomor");
        tokenpengguna = i.getStringExtra("token");
        nama.setText(i.getStringExtra("nama"));
        notelp.setText(i.getStringExtra("nomor"));

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Ini menu Arisan",Toast.LENGTH_SHORT).show();
                intent = new Intent(MenuActivity.this,MenuArisanActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"ini menu Logout",Toast.LENGTH_SHORT).show();
                intent = new Intent(MenuActivity.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
