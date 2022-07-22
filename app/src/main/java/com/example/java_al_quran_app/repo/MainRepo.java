package com.example.java_al_quran_app.repo;

import android.util.Log;

import com.example.java_al_quran_app.data.local.ListSuratModel;
import com.example.java_al_quran_app.data.local.RoomDao;
import com.example.java_al_quran_app.data.local.entities.ListSuratEntities;
import com.example.java_al_quran_app.data.network.Surat;
import com.example.java_al_quran_app.service.RetroService;

import java.util.ArrayList;
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

public class MainRepo {
    RetroService requestApi;
    RoomDao roomDao;

    public MainRepo(RetroService requestApi, RoomDao roomDao) {
        this.requestApi = requestApi;
        this.roomDao = roomDao;
    }

    public Future<Observable<List<Surat>>> getListSuratFutureCall() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Callable<Observable<List<Surat>>> myNetworkCallable = new Callable<Observable<List<Surat>>>() {
            @Override
            public Observable<List<Surat>> call() throws Exception {
                return requestApi.getListSurat();
            }
        };

        final Future<Observable<List<Surat>>> futureObservable = new Future<Observable<List<Surat>>>() {
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
            public Observable<List<Surat>> get() throws ExecutionException, InterruptedException {
                return executorService.submit(myNetworkCallable).get();
            }

            @Override
            public Observable<List<Surat>> get(long timeOut, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
                return executorService.submit(myNetworkCallable).get(timeOut, timeUnit);
            }
        };

        return futureObservable;
    }

    public void insertListSuratToDB(List<Surat> surat) {
        Completable.fromAction(() -> {
            roomDao.insertListSuratToDB(new ListSuratEntities(surat));
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("insertListSurat", "onSubscribe: OK");
            }

            @Override
            public void onComplete() {
                Log.e("insertListSurat", "onComplete: OK");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("insertListSurat", "onError: " + e.getMessage());
            }
        });
    }

    public Flowable<ListSuratEntities> getAllListSuratFromDB() {
        return roomDao.getListSuratFromDB();
    }
}
