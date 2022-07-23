package com.example.java_al_quran_app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.java_al_quran_app.R;
import com.example.java_al_quran_app.adapter.ListAyatAdapter;
import com.example.java_al_quran_app.adapter.ListSuratAdapter;
import com.example.java_al_quran_app.data.local.entities.ListAyatEntities;
import com.example.java_al_quran_app.data.network.Ayat;
import com.example.java_al_quran_app.data.network.Surat;
import com.example.java_al_quran_app.databinding.ActivityAyatBinding;
import com.example.java_al_quran_app.view_model.AyatViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class AyatRoomActivity extends AppCompatActivity {
    private ActivityAyatBinding activityAyatBinding;
    private String id, name;
    private CompositeDisposable compositeDisposable;
    private AyatViewModel ayatViewModel;
    private ListAyatAdapter listAyatAdapter;
    private ArrayList<Ayat> listAyatData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAyatBinding = DataBindingUtil.setContentView(AyatRoomActivity.this, R.layout.activity_ayat);
        compositeDisposable = new CompositeDisposable();

        //
        setupViewModel();

        getListAyatFromDB();
    }

    private void setupViewModel() {
        ayatViewModel = new ViewModelProvider(this).get(AyatViewModel.class);
    }

    private void getListAyatFromDB() {
        Disposable disposable = ayatViewModel.getListAyatFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ListAyatEntities>() {
                    @Override
                    public void accept(ListAyatEntities listAyatEntities) throws Throwable {

                        listAyatData = new ArrayList<>();
                        for (int i = 0; i < listAyatEntities.getListAyat().size(); i++) {
                            listAyatData.add(listAyatEntities.getListAyat().get(i));
                        }


                        // call adapter and recycler view
                        if (activityAyatBinding.rvAyat.getAdapter() != null) {
                            listAyatAdapter = (ListAyatAdapter) activityAyatBinding.rvAyat.getAdapter();
                            listAyatAdapter.updateData(listAyatData);
                        } else {
                            listAyatAdapter = new ListAyatAdapter(listAyatData);
                            activityAyatBinding.rvAyat.setAdapter(listAyatAdapter);
                        }
                    }
                });

        compositeDisposable.add(disposable);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}