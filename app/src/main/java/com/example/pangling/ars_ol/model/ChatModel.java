package com.example.pangling.ars_ol.model;

/**
 * Created by Pangling on 11/14/2017.
 */

public class ChatModel {
    int id;
    int isBot;
    String pengirim;
    String pesan;

    public ChatModel(int id, int isBot, String pengirim, String pesan) {
        this.id = id;
        this.isBot = isBot;
        this.pengirim = pengirim;
        this.pesan = pesan;
    }

    public int getId() {
        return id;
    }

    public int getIsBot() {
        return isBot;
    }

    public String getPengirim() {
        return pengirim;
    }

    public String getPesan() {
        return pesan;
    }
}
