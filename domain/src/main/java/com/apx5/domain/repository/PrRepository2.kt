package com.apx5.domain.repository

import com.apx5.domain.dto.*
import com.apx5.domain.param.*

interface PrRepository2 {
    suspend fun getServerStatus(): ServerStatusDto

    suspend fun getStatics(param: StaticsParam): StaticsDto

    suspend fun getRecordByTeams(param: TeamSummaryParam): TeamSummaryDto

    suspend fun getRecordDetail(param: TeamDetailParam): TeamDetailDto

    suspend fun getHistories(param: HistoriesParam): HistoriesDto

    suspend fun getPagingHistories(param: HistoriesParam, page: Int, size: Int): PagingResponse<HistoriesResponse>

    suspend fun delHistory(param: HistoryDelParam): HistoryDelDto

    suspend fun getDayGame(param: GameParam): GameDto

    suspend fun postNewGame(param: GameSaveParam): GameSaveDto

    suspend fun delUser(param: UserDelParam): UserDelDto

    suspend fun postUser(param: UserRegisterParam): UserRegisterDto
}