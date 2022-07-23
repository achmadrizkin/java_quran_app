package com.example.java_al_quran_app.repo;

import com.example.java_al_quran_app.data.local.RoomDao;
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

import io.reactivex.rxjava3.core.Observable;

public class AyatRepo {
    RetroService requestApi;
    RoomDao roomDao;

    public AyatRepo(RetroService requestApi, RoomDao roomDao) {
        this.requestApi = requestApi;
        this.roomDao = roomDao;
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

}
