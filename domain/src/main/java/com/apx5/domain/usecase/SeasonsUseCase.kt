package com.apx5.domain.usecase

import com.apx5.domain.param.TeamDetailParam
import com.apx5.domain.param.TeamSummaryParam
import com.apx5.domain.repository.PrRepository2

class SeasonsUseCase(private val prRepository2: PrRepository2) {
    suspend fun fetchRecordByTeams(param: TeamSummaryParam) = prRepository2.getRecordByTeams(param)
    suspend fun fetchRecordDetail(param: TeamDetailParam) = prRepository2.getRecordDetail(param)
}