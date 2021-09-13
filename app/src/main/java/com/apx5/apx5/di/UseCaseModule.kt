package com.apx5.apx5.di

import com.apx5.domain.repository.PrHistories
import com.apx5.domain.repository.PrRepository
import com.apx5.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * UseCaseModule
 */

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun provideCheckServerStatus(prRepository: PrRepository): SplashUseCase {
        return SplashUseCase(prRepository)
    }

    @Provides
    fun provideHistoriesUseCase(prRepository: PrRepository, prHistories: PrHistories): HistoriesUseCase {
        return HistoriesUseCase(prRepository, prHistories)
    }

    @Provides
    fun provideScheduledUseCase(prRepository: PrRepository): ScheduledUseCase {
        return ScheduledUseCase(prRepository)
    }

    @Provides
    fun provideSeasonsUseCase(prRepository: PrRepository): SeasonsUseCase {
        return SeasonsUseCase(prRepository)
    }

    @Provides
    fun provideSettingUseCase(prRepository: PrRepository): SettingUseCase {
        return SettingUseCase(prRepository)
    }

    @Provides
    fun provideStaticsUseCase(prRepository: PrRepository): StaticsUseCase {
        return StaticsUseCase(prRepository)
    }

    @Provides
    fun provideTeamUseCase(prRepository: PrRepository): TeamUseCase {
        return TeamUseCase(prRepository)
    }
}