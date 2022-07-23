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

import com.example.java_al_quran_app.R;
import com.example.java_al_quran_app.adapter.ListAyatAdapter;
import com.example.java_al_quran_app.adapter.ListSuratAdapter;
import com.example.java_al_quran_app.data.network.Ayat;
import com.example.java_al_quran_app.data.network.Surat;
import com.example.java_al_quran_app.databinding.ActivityAyatBinding;
import com.example.java_al_quran_app.view_model.AyatViewModel;
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
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class AyatActivity extends AppCompatActivity {
    private ActivityAyatBinding activityAyatBinding;
    private String id;
    private CompositeDisposable compositeDisposable;
    private AyatViewModel ayatViewModel;
    private ListAyatAdapter listAyatAdapter;
    private ArrayList<Ayat> listAyatData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAyatBinding = DataBindingUtil.setContentView(AyatActivity.this, R.layout.activity_ayat);
        compositeDisposable = new CompositeDisposable();
        listAyatData = new ArrayList<>();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        //
        activityAyatBinding.setIsLoading(true);

        //
        setupViewModel();

        // call api
        callListAyatByIdFromAPI(id);
    }

    private void setupViewModel() {
        ayatViewModel = new ViewModelProvider(this).get(AyatViewModel.class);
    }

    private void callListAyatByIdFromAPI(String id) {
        Observable.just(1)
                .flatMap(n -> ayatViewModel.getListAyatByIdFutureCall(id).get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Ayat>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Ayat> listAyat) {
                        Log.e("TAG", "------------ TEST API CALL --------------");
                        Log.e("TAG", "On Next" + listAyat.get(0).getAr());
                        Log.e("TAG", "On Next" + listAyat.get(1).getAr());
                        Log.e("TAG", "On Next" + listAyat.get(2).getAr());
                        Log.e("TAG", "On Next" + listAyat.get(3).getAr());

                        // insert to database -> recenly read (support offline)
                        ayatViewModel.insertListAyatToDB(listAyat);

                        // add data from API
                        listAyatData.addAll(listAyat);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("TAG", "onErrorL " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG", "Compleated");

                        // show adapter
                        activityAyatBinding.setIsLoading(false);
                        if (activityAyatBinding.rvAyat.getAdapter() != null) {
                            listAyatAdapter = (ListAyatAdapter) activityAyatBinding.rvAyat.getAdapter();
                            listAyatAdapter.updateData(listAyatData);
                        } else {
                            listAyatAdapter = new ListAyatAdapter(listAyatData);
                            activityAyatBinding.rvAyat.setAdapter(listAyatAdapter);
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}