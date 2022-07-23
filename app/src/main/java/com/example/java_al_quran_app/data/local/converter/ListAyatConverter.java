package com.example.java_al_quran_app.data.local.converter;

import androidx.room.TypeConverter;

import com.example.java_al_quran_app.data.network.Ayat;
import com.example.java_al_quran_app.data.network.Surat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListAyatConverter {
    @TypeConverter
    public String toJson(List<Ayat> ayatModel) {
        if (ayatModel == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Ayat>>() {}.getType();
        String json = gson.toJson(ayatModel, type);
        return json;
    }

    @TypeConverter
    public List<Ayat> toDataClass(String listAyat) {
        if (listAyat == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Ayat>>() {}.getType();
        List<Ayat> ayatModel = gson.fromJson(listAyat, type);
        return ayatModel;
    }
}
