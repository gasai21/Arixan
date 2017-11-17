package com.example.pangling.ars_ol.api;

import com.example.pangling.ars_ol.model.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Pangling on 11/14/2017.
 */

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("login.php")
    Call<Value> login(@Field("username")String username,
                      @Field("passwordd")String password);

    @FormUrlEncoded
    @POST("bikingrup.php")
    Call<Value> bikinG(@Field("namagrup")String namagrup,
                       @Field("namapengguna")String namapengguna,
                       @Field("nomor")String nomor,
                       @Field("token")String token);

    @FormUrlEncoded
    @POST("viewgrup.php")
    Call<Value> tampilGrup(@Field("namaakun")String namaakun);

    @FormUrlEncoded
    @POST("viewanggota.php")
    Call<Value> tampilAnggota(@Field("namaakun")String akun);

    @FormUrlEncoded
    @POST("tambahAnggota.php")
    Call<Value> tambahAnggota(@Field("namapengguna")String nama,
                              @Field("namagrup")String grup,
                              @Field("nomorTelp")String nomor);

    @FormUrlEncoded
    @POST("deleteanggota.php")
    Call<Value> deleteAnggota(@Field("id")String id);

    @FormUrlEncoded
    @POST("RegisterDevice.php")
    Call<Value> tambahAkun(@Field("nama") String nama,
                           @Field("username")String username,
                           @Field("password")String password,
                           @Field("noTelp")String nomor,
                           @Field("token")String token);

    @FormUrlEncoded
    @POST("sendMultiplePush.php")
    Call<Value> pushNotif(@Field("grup")String grup,
                          @Field("title")String title,
                          @Field("message")String message);
}
