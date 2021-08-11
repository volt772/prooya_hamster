package com.apx5.apx5.di

import com.apx5.apx5.network.api.PrApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { get<Retrofit>().create(PrApi::class.java) }
}