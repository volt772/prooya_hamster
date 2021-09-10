package com.apx5.apx5.di

import com.apx5.domain.repository.PrRepository2
import com.apx5.domain.usecase.CheckServerStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun provideCheckServerStatus(prRepository: PrRepository2): CheckServerStatus {
        return CheckServerStatus(prRepository)
    }
}