package com.apx5.apx5.di

import com.apx5.domain.repository.PrHistories
import com.apx5.domain.repository.PrRepository2
import com.apx5.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun provideCheckServerStatus(prRepository: PrRepository2): SplashUseCase {
        return SplashUseCase(prRepository)
    }

    @Provides
    fun provideHistoriesUseCase(prRepository: PrRepository2, prHistories: PrHistories): HistoriesUseCase {
        return HistoriesUseCase(prRepository, prHistories)
    }

    @Provides
    fun provideScheduledUseCase(prRepository: PrRepository2): ScheduledUseCase {
        return ScheduledUseCase(prRepository)
    }

    @Provides
    fun provideSeasonsUseCase(prRepository: PrRepository2): SeasonsUseCase {
        return SeasonsUseCase(prRepository)
    }

    @Provides
    fun provideSettingUseCase(prRepository: PrRepository2): SettingUseCase {
        return SettingUseCase(prRepository)
    }

    @Provides
    fun provideStaticsUseCase(prRepository: PrRepository2): StaticsUseCase {
        return StaticsUseCase(prRepository)
    }

    @Provides
    fun provideTeamUseCase(prRepository: PrRepository2): TeamUseCase {
        return TeamUseCase(prRepository)
    }
}