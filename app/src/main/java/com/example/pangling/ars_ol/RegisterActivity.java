package com.example.pangling.ars_ol;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pangling.ars_ol.URL.URL;
import com.example.pangling.ars_ol.api.RegisterAPI;
import com.example.pangling.ars_ol.model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //defining views
    EditText nama,username,password,nomor;
    Button btnRegister;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        //getting views from xml
        nama = (EditText) findViewById(R.id.txtNamaRegister);
        username = (EditText) findViewById(R.id.txtusernameRegister);
        password = (EditText) findViewById(R.id.txtpasswordRegister);
        nomor = (EditText) findViewById(R.id.txtnomorRegister);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        //adding listener to view
        btnRegister.setOnClickListener(this);
    }

    //storing token to mysql server
    private void sendTokenToServer() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        final String namaA = nama.getText().toString();
        final String usernameA = username.getText().toString();
        final String passwordA = password.getText().toString();
        final String nomorA = nomor.getText().toString();

        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.tambahAkun(namaA,usernameA,passwordA,nomorA,token);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                progressDialog.dismiss();
                String message = response.body().getMessage();
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Gagal Koneksi!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btnRegister) {
            sendTokenToServer();
        }
    }
}