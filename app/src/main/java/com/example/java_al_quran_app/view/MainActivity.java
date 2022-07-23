package com.example.java_al_quran_app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.java_al_quran_app.R;
import com.example.java_al_quran_app.adapter.ListSuratAdapter;
import com.example.java_al_quran_app.data.local.entities.ListAyatEntities;
import com.example.java_al_quran_app.data.local.entities.ListSuratEntities;
import com.example.java_al_quran_app.data.network.Ayat;
import com.example.java_al_quran_app.data.network.Surat;
import com.example.java_al_quran_app.databinding.ActivityMainBinding;
import com.example.java_al_quran_app.view_model.AyatViewModel;
import com.example.java_al_quran_app.view_model.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private CompositeDisposable compositeDisposable, compositeDisposableListSurahDB, compositeDisposableListAyat;
    private MainViewModel mainViewModel;
    private AyatViewModel ayatViewModel;
    private ArrayList<Surat> listSuratData;
    ListSuratAdapter listSuratAdapter;

    @Inject
    ConnectivityManager connectivityManager;

    @Inject
    NetworkRequest networkRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        compositeDisposable = new CompositeDisposable();
        compositeDisposableListSurahDB = new CompositeDisposable();
        compositeDisposableListAyat = new CompositeDisposable();

        // setup view model
        setupViewModel();

        //
        listSuratAdapter = new ListSuratAdapter(listSuratData);

        // check loading
        activityMainBinding.setIsLoading(true);
        activityMainBinding.setIsRead(true);

        // check connection
        checkConnection();

        // calling api (network)
        callListSuratFromAPI();

        // room
        getListSuratFromDB();
        getListAyatFromDB();

        //
        activityMainBinding.clRecentlyRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AyatRoomActivity.class);
                startActivity(i);
            }
        });
    }

    private void setupViewModel() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        ayatViewModel = new ViewModelProvider(this).get(AyatViewModel.class);
    }


    private void checkConnection() {
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@androidx.annotation.NonNull Network network) {
                super.onAvailable(network);
                callListSuratFromAPI();
            }

            @Override
            public void onLost(@androidx.annotation.NonNull Network network) {
                super.onLost(network);
                Snackbar.make(activityMainBinding.clMain, "Internet Connection Lost", 2000).show();
            }
        };

        // check build
        checkBuild(networkCallback);
    }

    private void callListSuratFromAPI() {
        Observable.just(1)
                .flatMap(n -> mainViewModel.getListSuratFutureCall().get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Surat>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Surat> listSurat) {
                        Log.e("TAG", "------------ TEST API CALL --------------");
                        Log.e("TAG", "On Next" + listSurat.get(0).getNama());
                        Log.e("TAG", "On Next" + listSurat.get(1).getNama());
                        Log.e("TAG", "On Next" + listSurat.get(2).getNama());
                        Log.e("TAG", "On Next" + listSurat.get(3).getNama());

                        // insert to database
                        mainViewModel.insertListSuratToDB(listSurat);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("TAG", "onErrorL " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG", "Compleated");
                    }
                });
    }

    private void getListAyatFromDB() {
        Disposable disposable = ayatViewModel.getListAyatFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListAyatEntities>() {
                    @Override
                    public void accept(ListAyatEntities listAyatEntities) throws Throwable {
                        activityMainBinding.setRecentlyAr(listAyatEntities.getListAyat().get(0).getAr());
                        activityMainBinding.setRecentlyTerjemahan(listAyatEntities.getListAyat().get(0).getId());
                    }
                });

        compositeDisposableListAyat.add(disposable);
    }


    private void getListSuratFromDB() {
        Disposable disposable = mainViewModel.getAllListSuratFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListSuratEntities>() {
                    @Override
                    public void accept(ListSuratEntities listSurat) throws Throwable {
                        Log.e("TAG", "------------ TEST ROOM DATABASE --------------");

                        Log.e("TAG", "ROOM: ON NEXT: " + listSurat.getListSurat().get(0).getNama());
                        Log.e("TAG", "ROOM: ON NEXT: " + listSurat.getListSurat().get(1).getNama());
                        Log.e("TAG", "ROOM: ON NEXT: " + listSurat.getListSurat().get(2).getNama());
                        Log.e("TAG", "ROOM: ON NEXT: " + listSurat.getListSurat().get(3).getNama());

                        listSuratData = new ArrayList<>();
                        for (int i = 0; i < listSurat.getListSurat().size(); i++) {
                            listSuratData.add(listSurat.getListSurat().get(i));
                        }

                        // call adapter and recycler view
                        if (activityMainBinding.rvListSurat.getAdapter() != null) {
                            listSuratAdapter = (ListSuratAdapter) activityMainBinding.rvListSurat.getAdapter();
                            listSuratAdapter.updateData(listSuratData);
                        } else {
                            listSuratAdapter = new ListSuratAdapter(listSuratData);
                            activityMainBinding.rvListSurat.setAdapter(listSuratAdapter);
                        }


                        activityMainBinding.setIsLoading(false);
                    }
                });

        compositeDisposableListSurahDB.add(disposable);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        compositeDisposableListSurahDB.clear();
        compositeDisposableListAyat.clear();
    }

    private void checkBuild(ConnectivityManager.NetworkCallback networkCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        }
    }
}