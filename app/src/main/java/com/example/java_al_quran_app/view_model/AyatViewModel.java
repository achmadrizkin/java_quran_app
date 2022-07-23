package com.example.java_al_quran_app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.java_al_quran_app.data.network.Ayat;
import com.example.java_al_quran_app.data.network.Surat;
import com.example.java_al_quran_app.repo.AyatRepo;
import com.example.java_al_quran_app.repo.MainRepo;

import java.util.List;
import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class AyatViewModel extends AndroidViewModel {
    @Inject
    AyatRepo ayatRepo;

    @Inject
    public AyatViewModel(@NonNull Application application) {
        super(application);
    }

    public Future<Observable<List<Ayat>>> getListAyatByIdFutureCall(String id) {
        return ayatRepo.getListAyatByIdFutureCall(id);
    }
}
