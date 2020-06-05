package com.apx5.apx5.network.api

import com.apx5.apx5.constants.PrConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RemoteApplication
 */

class PrApiService private constructor() {
    fun getService(): PrApi {
        return retrofit.create(PrApi::class.java)
    }

    companion object {
        private var instance: PrApiService? = null
        private lateinit var retrofit: Retrofit
        private const val BASE_URL = PrConstants.App.FLK_HOST

        @Synchronized
        fun getInstance(): PrApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            if (instance == null) {
                instance = PrApiService()
            }

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return instance as PrApiService
        }
    }
}