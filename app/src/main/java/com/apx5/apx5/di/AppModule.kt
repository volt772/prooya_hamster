package com.apx5.apx5.di

import android.app.Application
import android.content.Context
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.storage.PrPreferenceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * AppModule
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun bindPrPreferences(impl: PrPreferenceImpl): PrPreference
}