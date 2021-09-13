package com.apx5.domain.usecase

import com.apx5.domain.param.StaticsParam
import com.apx5.domain.repository.PrRepository2

class StaticsUseCase(private val prRepository2: PrRepository2) {
    suspend fun fetchStatics(param: StaticsParam) = prRepository2.getStatics(param)
}