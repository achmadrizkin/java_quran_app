package com.example.java_al_quran_app.hilt.network;

import com.example.java_al_quran_app.data.local.RoomDao;
import com.example.java_al_quran_app.repo.MainRepo;
import com.example.java_al_quran_app.service.RetroService;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class HiltNetworkModule {
    String BASE_URL = "https://al-quran-8d642.firebaseio.com/";

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder().addInterceptor(interceptor).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .build();
                return chain.proceed(request);
            }
        }).build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    RetroService ProvideRequestApi(Retrofit retrofit){
        return retrofit.create(RetroService.class);
    }

    @Provides
    @Singleton
    MainRepo mainRepository(RetroService requestApi, RoomDao roomDao) {
        return new MainRepo(requestApi, roomDao);
    }
}
