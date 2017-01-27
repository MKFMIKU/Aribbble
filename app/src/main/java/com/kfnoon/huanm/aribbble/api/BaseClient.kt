package com.kfnoon.huanm.aribbble.api

import com.kfnoon.huanm.aribbble.model.Comment
import com.kfnoon.huanm.aribbble.model.Shot


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

class BaseClient {

    private val mApiService: ApiService
    private var sInstance: BaseClient? = null

    init {
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer 5e897d1016046345ecb18d6e4019251f540f0249c2317b7685d66e52f8ae6c8d")
                    .build()
            chain.proceed(request)
        }
        val client = builder.build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.dribbble.com/v1/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        mApiService = retrofit.create(ApiService::class.java)

    }

    fun getShots(page: Int): Observable<List<Shot>> {
        return mApiService.getShots(page)
    }

    fun getShot(id: Int): Observable<Shot> {
        return mApiService.getShot(id)
    }

    fun getShotComments(id: Int): Observable<List<Comment>> {
        return mApiService.getShotComments(id)
    }

    private interface ApiService {
        @GET("shots")
        fun getShots(@Query("page") page: Int): Observable<List<Shot>>

        @GET("shots/{id}")
        fun getShot(@Path("id") id: Int): Observable<Shot>

        @GET("shots/{id}/comments")
        fun getShotComments(@Path("id") id: Int): Observable<List<Comment>>
    }
}
