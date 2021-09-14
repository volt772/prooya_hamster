package com.apx5.domain.usecase

import com.apx5.domain.param.HistoriesParam
import com.apx5.domain.param.HistoryDelParam
import com.apx5.domain.repository.PrHistories
import com.apx5.domain.repository.PrRepository

/**
 * HistoriesUseCase
 */

class HistoriesUseCase(
    private val prRepository: PrRepository,
    private val prHistories: PrHistories
) {

    fun fetchHistories(param: HistoriesParam) = prHistories.getHistories(param)

    suspend fun delHistory(param: HistoryDelParam) = prRepository.delHistory(param)
}