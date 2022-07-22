package com.example.java_al_quran_app.data.local;

import com.example.java_al_quran_app.data.network.Surat;

import java.util.List;

public class ListSuratModel {
    private List<Surat> listSurat;

    public List<Surat> getListSurat() {
        return listSurat;
    }

    public void setListSurat(List<Surat> listSurat) {
        this.listSurat = listSurat;
    }
}
