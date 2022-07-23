package com.example.java_al_quran_app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.java_al_quran_app.data.local.entities.ListSuratEntities;
import com.example.java_al_quran_app.data.network.Surat;
import com.example.java_al_quran_app.repo.MainRepo;

import java.util.List;
import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class MainViewModel extends AndroidViewModel {
    @Inject
    MainRepo mainRepository;

    @Inject
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public Future<Observable<List<Surat>>> getListSuratFutureCall() {
        return mainRepository.getListSuratFutureCall();
    }

    public void insertListSuratToDB(List<Surat> surat) {
        mainRepository.insertListSuratToDB(surat);
    }

    public Flowable<ListSuratEntities> getAllListSuratFromDB() {
        return mainRepository.getAllListSuratFromDB();
    }
}
