package com.apx5.domain.usecase

import com.apx5.domain.param.HistoriesParam
import com.apx5.domain.param.HistoryDelParam
import com.apx5.domain.repository.PrHistories
import com.apx5.domain.repository.PrRepository2

class HistoriesUseCase(private val prRepository2: PrRepository2, private val prHistories: PrHistories) {
    fun fetchHistories(param: HistoriesParam) = prHistories.getHistories(param)

    suspend fun delHistory(param: HistoryDelParam) = prRepository2.delHistory(param)
}