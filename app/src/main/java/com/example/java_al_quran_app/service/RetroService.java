package com.example.java_al_quran_app.service;

import com.example.java_al_quran_app.data.network.Ayat;
import com.example.java_al_quran_app.data.network.Surat;


import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetroService {
    @GET("data")
    Observable<List<Surat>> getListSurat();

    @GET("surat/{id}")
    Observable<List<Ayat>> getListAyatById(@Path("id") String id);
}
