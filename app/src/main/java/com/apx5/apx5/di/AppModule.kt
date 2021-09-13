package com.apx5.apx5.di

import android.app.Application
import android.content.Context
import com.apx5.apx5.mapper.HistoriesMapper
import com.apx5.apx5.mapper.HistoriesMapperImpl
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.storage.PrPreferenceImpl
import com.apx5.apx5.ui.utilities.PrUtils
import com.apx5.apx5.ui.utilities.PrUtilsImpl
import com.apx5.data.data_source.HistoriesRemoteDataSource
import com.apx5.data.data_source.HistoriesRemoteDataSourceImpl
import com.apx5.data.repository.PrHistoriesImpl
import com.apx5.data.repository.PrRepositoryImpl
import com.apx5.domain.repository.PrHistories
import com.apx5.domain.repository.PrRepository
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

    @Binds
    @Singleton
    abstract fun bindPrRepository(impl: PrRepositoryImpl): PrRepository

    @Binds
    @Singleton
    abstract fun bindPrHistories(impl: PrHistoriesImpl): PrHistories

    @Binds
    @Singleton
    abstract fun bindHistoriesRemoteDateSource(impl: HistoriesRemoteDataSourceImpl): HistoriesRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindHistoriesMapper(impl: HistoriesMapperImpl): HistoriesMapper
}