package com.example.pangling.ars_ol.model;

import java.util.List;

/**
 * Created by Pangling on 11/14/2017.
 */

public class Value {
    String message;
    String value;
    String namaAkun;
    String noTelpAkun;
    String token;
    List<Grup> ambilH;
    List<ModelAnggota> ambilA;

    public String getToken() {
        return token;
    }

    public List<ModelAnggota> getAmbilA() {
        return ambilA;
    }

    public List<Grup> getAmbilH() {
        return ambilH;
    }

    public String getMessage() {
        return message;
    }

    public String getValue() {
        return value;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public String getNoTelpAkun() {
        return noTelpAkun;
    }
}
