package com.apx5.domain.repository

import com.apx5.domain.dto.HistoriesResponse
import com.apx5.domain.dto.PagingResponse
import com.apx5.domain.model.*
import com.apx5.domain.param.*

/**
 * PrRepository
 */

interface PrRepository {

    suspend fun getServerStatus(): PrServerStatus

    suspend fun getStatics(param: StaticsParam): PrStatics

    suspend fun getRecordByTeams(param: TeamSummaryParam): PrTeamSummary

    suspend fun getRecordDetail(param: TeamDetailParam): PrTeamDetail

    suspend fun getPagingHistories(param: HistoriesParam, page: Int, size: Int): PagingResponse<HistoriesResponse>

    suspend fun delHistory(param: HistoryDelParam): PrDelHistory

    suspend fun getDayGame(param: GameParam): PrGame

    suspend fun postNewGame(param: GameSaveParam): PrGameSave

    suspend fun delUser(param: UserDelParam): PrDelUser

    suspend fun postUser(param: UserRegisterParam): PrUserRegister
}