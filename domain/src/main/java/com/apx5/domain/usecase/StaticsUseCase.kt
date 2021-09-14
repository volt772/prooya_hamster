package com.apx5.domain.usecase

import com.apx5.domain.param.StaticsParam
import com.apx5.domain.repository.PrRepository

/**
 * StaticsUseCase
 */

class StaticsUseCase(
    private val prRepository: PrRepository
) {

    suspend fun fetchStatics(param: StaticsParam) = prRepository.getStatics(param)
}