package com.kfnoon.huanm.aribbble.api;

import com.kfnoon.huanm.aribbble.model.Shot;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public class BaseClient {

    private final ApiService mApiService;
    private static volatile BaseClient sInstance;

    public static BaseClient instance() {
        return sInstance == null ? sInstance = new BaseClient() : sInstance;
    }

    public BaseClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer 5e897d1016046345ecb18d6e4019251f540f0249c2317b7685d66e52f8ae6c8d")
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dribbble.com/v1/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mApiService = retrofit.create(ApiService.class);

    }

    public Observable<List<Shot>> getShots(int page){
        return mApiService.getShots(page);
    }

    public Observable<Shot> getShot(int id){
        return mApiService.getShot(id);
    }

    private interface ApiService {
        @GET("shots")
        Observable<List<Shot>> getShots(@Query("page") int page);

        @GET("shots/{id}")
        Observable<Shot> getShot(@Path("id") int id);
    }
}
