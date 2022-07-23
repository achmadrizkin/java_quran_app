package com.example.java_al_quran_app.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.java_al_quran_app.data.network.Ayat;
import java.util.List;

@Entity(tableName = "listAyat")
public class ListAyatEntities {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "listAyat")
    public List<Ayat> ayat;

    public ListAyatEntities(List<Ayat> ayat) {
        this.ayat = ayat;
    }

    public int getUid() {
        return uid;
    }

    public List<Ayat> getListAyat() {
        return ayat;
    }
}
