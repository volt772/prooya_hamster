package com.apx5.domain.usecase

import com.apx5.domain.param.TeamDetailParam
import com.apx5.domain.param.TeamSummaryParam
import com.apx5.domain.repository.PrRepository

/**
 * SeasonsUseCase
 */

class SeasonsUseCase(
    private val prRepository: PrRepository
) {

    suspend fun fetchRecordByTeams(param: TeamSummaryParam) = prRepository.getRecordByTeams(param)

    suspend fun fetchRecordDetail(param: TeamDetailParam) = prRepository.getRecordDetail(param)
}