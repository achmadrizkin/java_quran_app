package com.example.java_al_quran_app.repo;

import android.util.Log;

import com.example.java_al_quran_app.data.local.ListAyatRoomDao;
import com.example.java_al_quran_app.data.local.RoomDao;
import com.example.java_al_quran_app.data.local.entities.ListAyatEntities;
import com.example.java_al_quran_app.data.local.entities.ListSuratEntities;
import com.example.java_al_quran_app.data.network.Ayat;
import com.example.java_al_quran_app.data.network.Surat;
import com.example.java_al_quran_app.service.RetroService;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AyatRepo {
    RetroService requestApi;
    ListAyatRoomDao listAyatRoomDao;

    public AyatRepo(RetroService requestApi, ListAyatRoomDao listAyatRoomDao) {
        this.requestApi = requestApi;
        this.listAyatRoomDao = listAyatRoomDao;
    }

    public Future<Observable<List<Ayat>>> getListAyatByIdFutureCall(String id) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Callable<Observable<List<Ayat>>> myNetworkCallable = new Callable<Observable<List<Ayat>>>() {
            @Override
            public Observable<List<Ayat>> call() throws Exception {
                return requestApi.getListAyatById(id);
            }
        };

        final Future<Observable<List<Ayat>>> futureObservable = new Future<Observable<List<Ayat>>>() {
            @Override
            public boolean cancel(boolean b) {
                if (b) {
                    executorService.shutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executorService.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executorService.isTerminated();
            }

            @Override
            public Observable<List<Ayat>> get() throws ExecutionException, InterruptedException {
                return executorService.submit(myNetworkCallable).get();
            }

            @Override
            public Observable<List<Ayat>> get(long timeOut, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
                return executorService.submit(myNetworkCallable).get(timeOut, timeUnit);
            }
        };

        return futureObservable;
    }


    public void insertListAyatToDB(List<Ayat> ayat) {
        Completable.fromAction(() -> {
            listAyatRoomDao.insertListAyatToDB(new ListAyatEntities(ayat));
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("insertListAyat", "onSubscribe: OK");
            }

            @Override
            public void onComplete() {
                Log.e("insertListAyat", "------------------ INSERT LIST AYAT DATABASE SUCCESS ------------------");
                Log.e("insertListAyat", "onComplete: OK");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("insertListAyat", "onError: " + e.getMessage());
            }
        });
    }

    public Flowable<ListAyatEntities> getListAyatFromDB() {
        return listAyatRoomDao.getListAyatFromDB();
    }

}
