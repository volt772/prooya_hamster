package com.apx5.apx5.remote

import android.util.Log

import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.network.PrApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RemoteApplication
 */

class RemoteApplication {
    val remoteService: PrApi
        get() {
            val logging = HttpLoggingInterceptor { message -> Log.d("API LOG", message) }

            logging.level = HttpLoggingInterceptor.Level.BASIC
            val client = OkHttpClient.Builder().addInterceptor(logging).build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(PrConstants.App.FLK_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(PrApi::class.java)
        }
}