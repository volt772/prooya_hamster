package com.apx5.domain.usecase

import com.apx5.domain.param.GameParam
import com.apx5.domain.param.GameSaveParam
import com.apx5.domain.repository.PrRepository

/**
 * ScheduledUseCase
 */

class ScheduledUseCase(
    private val prRepository: PrRepository
) {

    suspend fun fetchDayGame(param: GameParam) = prRepository.getDayGame(param)

    suspend fun saveNewGame(param: GameSaveParam) = prRepository.postNewGame(param)
}