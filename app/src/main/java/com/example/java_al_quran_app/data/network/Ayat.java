package com.example.java_al_quran_app.data.network;

import com.google.gson.annotations.SerializedName;

public class Ayat {
    @SerializedName("ar")
    private String ar;

    @SerializedName("id")
    private String id;

    @SerializedName("nomor")
    private String nomor;

    @SerializedName("tr")
    private String tr;

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getTr() {
        return tr;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }
}
