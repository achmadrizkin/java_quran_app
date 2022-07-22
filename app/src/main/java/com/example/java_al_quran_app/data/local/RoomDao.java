package com.example.java_al_quran_app.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.java_al_quran_app.data.local.entities.ListSuratEntities;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface RoomDao {
    // insert list surat to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListSuratToDB(ListSuratEntities listSurat);

    @Query("SELECT * FROM listSurat")
    Flowable<ListSuratEntities> getListSuratFromDB();

    // insert list ayat to database
}
