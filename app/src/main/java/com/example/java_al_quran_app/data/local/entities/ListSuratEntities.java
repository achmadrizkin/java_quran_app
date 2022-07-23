package com.example.java_al_quran_app.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.java_al_quran_app.data.network.Surat;

import java.util.List;

@Entity(tableName = "listSurat")
public class ListSuratEntities {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "listSurat")
    public List<Surat> surat;

    public ListSuratEntities(List<Surat> surat) {
        this.surat = surat;
    }

    public int getUid() {
        return uid;
    }

    public List<Surat> getListSurat() {
        return surat;
    }
}
