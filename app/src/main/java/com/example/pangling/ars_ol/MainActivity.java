package com.example.pangling.ars_ol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pangling.ars_ol.URL.URL;
import com.example.pangling.ars_ol.api.RegisterAPI;
import com.example.pangling.ars_ol.model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText username,password;
    ProgressDialog progressDialog;
    Intent intent;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //ini untuk inisialisasi
        btnLogin = (Button) findViewById(R.id.btnLogin);
        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPassword);
        register = (TextView) findViewById(R.id.tvRegistr);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        //ini untuk mengatur progres dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(username.getText().toString(),password.getText().toString());
            }
        });
    }

    public void login(String uc, String pw){
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.login(uc,pw);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if(value.equals("1")){
                    progressDialog.dismiss();
                    String nama = response.body().getNamaAkun();
                    String nomor = response.body().getNoTelpAkun();
                    String token = response.body().getToken();
                    intent = new Intent(MainActivity.this,MenuActivity.class);
                    intent.putExtra("nama",nama);
                    intent.putExtra("nomor",nomor);
                    intent.putExtra("token",token);
                    finish();
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Gagal Koneksi",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
