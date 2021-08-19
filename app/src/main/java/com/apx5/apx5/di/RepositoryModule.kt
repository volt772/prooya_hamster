package com.apx5.apx5.di

import com.apx5.apx5.network.api.PrApiService
import com.apx5.apx5.repository.PrRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * RepositoryModule
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePrRepository(prApi: PrApiService): PrRepository {
        return PrRepository(prApi)
    }
}