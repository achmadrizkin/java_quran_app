package com.example.java_al_quran_app.data.network;

import com.google.gson.annotations.SerializedName;

public class Surat {
    @SerializedName("arti")
    private String arti;

    @SerializedName("asma")
    private String asma;

    @SerializedName("audio")
    private String audio;

    @SerializedName("ayat")
    private long ayat;

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("nama")
    private String nama;

    @SerializedName("nomor")
    private String nomor;

    @SerializedName("rukuk")
    private String rukuk;

    @SerializedName("type")
    private String type;

    @SerializedName("urut")
    private String urut;

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

    public String getAsma() {
        return asma;
    }

    public void setAsma(String asma) {
        this.asma = asma;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public long getAyat() {
        return ayat;
    }

    public void setAyat(long ayat) {
        this.ayat = ayat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getRukuk() {
        return rukuk;
    }

    public void setRukuk(String rukuk) {
        this.rukuk = rukuk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrut() {
        return urut;
    }

    public void setUrut(String urut) {
        this.urut = urut;
    }
}
