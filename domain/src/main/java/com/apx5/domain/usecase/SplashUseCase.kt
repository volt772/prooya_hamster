package com.apx5.domain.usecase

import com.apx5.domain.repository.PrRepository2

class SplashUseCase(private val prRepository2: PrRepository2) {
    suspend fun serverStatus() = prRepository2.getServerStatus()
}