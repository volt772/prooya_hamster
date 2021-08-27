package com.apx5.apx5.di

import android.app.Application
import android.content.Context
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.storage.PrPreferenceImpl
import com.apx5.apx5.ui.utilities.PrUtils
import com.apx5.apx5.ui.utilities.PrUtilsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * AppModule
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context

    @Binds
    @Singleton
    abstract fun bindPrPreferences(impl: PrPreferenceImpl): PrPreference

    @Binds
    @Singleton
    abstract fun bindPrUtils(impl: PrUtilsImpl): PrUtils
}