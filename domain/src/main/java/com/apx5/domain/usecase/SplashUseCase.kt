package com.apx5.domain.usecase

import com.apx5.domain.repository.PrRepository

/**
 * SplashUseCase
 */

class SplashUseCase(
    private val prRepository: PrRepository
) {

    suspend fun serverStatus() = prRepository.getServerStatus()
}