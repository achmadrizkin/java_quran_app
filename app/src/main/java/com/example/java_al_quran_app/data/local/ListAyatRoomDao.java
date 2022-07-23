package com.example.java_al_quran_app.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.java_al_quran_app.data.local.entities.ListAyatEntities;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ListAyatRoomDao {
    // insert list surat to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListAyatToDB(ListAyatEntities listAyatEntities);

    @Query("SELECT * FROM listAyat")
    Flowable<ListAyatEntities> getListAyatFromDB();
}
