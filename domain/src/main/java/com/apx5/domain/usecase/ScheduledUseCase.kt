package com.apx5.domain.usecase

import com.apx5.domain.param.GameParam
import com.apx5.domain.param.GameSaveParam
import com.apx5.domain.repository.PrRepository2

class ScheduledUseCase(private val prRepository2: PrRepository2) {
    suspend fun fetchDayGame(param: GameParam) = prRepository2.getDayGame(param)
    suspend fun saveNewGame(param: GameSaveParam) = prRepository2.postNewGame(param)
}