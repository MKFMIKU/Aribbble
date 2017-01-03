package com.kfnoon.huanm.aribbble.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseClient {

    private BaseClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dribbble.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
