package com.kfnoon.huanm.aribbble.api;

import com.kfnoon.huanm.aribbble.model.Shot;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class BaseClient {

    public ApiService mApiService;

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
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mApiService = retrofit.create(ApiService.class);

    }

    private interface ApiService {
        @GET("shots")
        Call<List<Shot>> getShots(@Query("page") int page);

        @GET("shots/{id}")
        Call<Shot> getShot(@Path("id") int id);
    }
}
