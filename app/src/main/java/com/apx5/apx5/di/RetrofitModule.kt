package com.apx5.apx5.di

import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.network.api.PrApiService
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 15L
private const val WRITE_TIMEOUT = 15L
private const val READ_TIMEOUT = 15L

val retrofitModule = module {
    single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }
    single { GsonBuilder().create() }
    single { retrofitHttpClient() }
    single { retrofitBuilder() }
}

private fun Scope.retrofitBuilder(): Retrofit {
    return Retrofit.Builder()
    .baseUrl(PrConstants.App.FLK_HOST)
    .addConverterFactory(GsonConverterFactory.create())
    .client(get())
    .build()
}

private fun Scope.retrofitHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        cache(get())
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }.build()

//    val interceptor = HttpLoggingInterceptor()
//    interceptor.level = HttpLoggingInterceptor.Level.BODY
//    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//
//    if (PrApiService.instance == null) {
//        PrApiService.instance = PrApiService()
//    }
}


//val interceptor = HttpLoggingInterceptor()
//interceptor.level = HttpLoggingInterceptor.Level.BODY
//val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//
//if (instance == null) {
//    PrApiService.instance = PrApiService()
//}
//
//retrofit = Retrofit.Builder()
//.baseUrl(BASE_URL)
//.addConverterFactory(GsonConverterFactory.create())
//.client(client)
//.build()
//
//return instance as PrApiService
//}
