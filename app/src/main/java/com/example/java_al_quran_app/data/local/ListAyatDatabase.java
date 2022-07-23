package com.example.java_al_quran_app.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.java_al_quran_app.data.local.converter.ListAyatConverter;
import com.example.java_al_quran_app.data.local.entities.ListAyatEntities;

@TypeConverters({ListAyatConverter.class})
@Database(entities = {ListAyatEntities.class}, version = 1)
public abstract class ListAyatDatabase extends RoomDatabase {
    public static final String db_name = "listAyat";
    private static ListAyatDatabase instance;

    public abstract ListAyatRoomDao roomDao();

    public static synchronized ListAyatDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ListAyatDatabase.class, db_name)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
