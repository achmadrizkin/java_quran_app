package com.example.java_al_quran_app.hilt.local;

import android.content.Context;

import com.example.java_al_quran_app.data.local.AppDatabase;
import com.example.java_al_quran_app.data.local.RoomDao;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class HiltRoomModule {
    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    RoomDao provideRoomDao(AppDatabase appDatabase) {
        return appDatabase.roomDao();
    }
}
