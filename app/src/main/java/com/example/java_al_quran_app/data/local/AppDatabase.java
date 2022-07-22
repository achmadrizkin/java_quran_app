package com.example.java_al_quran_app.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.java_al_quran_app.data.local.converter.ListSuratConverter;
import com.example.java_al_quran_app.data.local.entities.ListSuratEntities;

@TypeConverters({ListSuratConverter.class})
@Database(entities = {ListSuratEntities.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String db_name = "appDB";
    private static AppDatabase instance;

    public abstract RoomDao roomDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, db_name)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
