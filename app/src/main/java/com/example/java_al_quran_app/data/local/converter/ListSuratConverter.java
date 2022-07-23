package com.example.java_al_quran_app.data.local.converter;

import androidx.room.TypeConverter;

import com.example.java_al_quran_app.data.network.Surat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListSuratConverter {
    @TypeConverter
    public String toJson(List<Surat> suratModel) {
        if (suratModel == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Surat>>() {}.getType();
        String json = gson.toJson(suratModel, type);
        return json;
    }

    @TypeConverter
    public List<Surat> toDataClass(String listSurat) {
        if (listSurat == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Surat>>() {}.getType();
        List<Surat> suratModel = gson.fromJson(listSurat, type);
        return suratModel;
    }
}
