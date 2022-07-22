package com.example.java_al_quran_app.service;

import com.example.java_al_quran_app.data.network.ListAyat;
import com.example.java_al_quran_app.data.network.Surat;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface RetroService {
    @GET("data.json?print=pretty")
    Observable<List<Surat>> getListSurat();

    @GET("surat/1.json?print=pretty")
    Observable<ListAyat> getListAyat();
}
