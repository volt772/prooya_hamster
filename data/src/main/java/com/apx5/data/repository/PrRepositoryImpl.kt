package com.apx5.data.repository

import com.apx5.data.network.PrApiService
import com.apx5.domain.dto.*
import com.apx5.domain.model.*
import com.apx5.domain.param.*
import com.apx5.domain.repository.PrRepository
import javax.inject.Inject

/**
 * PrRepositoryImpl
 */

class PrRepositoryImpl @Inject constructor(
    private val prApiService: PrApiService
): PrRepository {

    override suspend fun getServerStatus(): PrServerStatus {
        val resp = prApiService.getServerStatus()
        return resp.data?: ServerStatusDto()
    }

    override suspend fun getStatics(param: StaticsParam): PrStatics {
        val resp = prApiService.getStatics(param)
        return resp.data?: StaticsDto()
    }

    override suspend fun getRecordByTeams(param: TeamSummaryParam): PrTeamSummary {
        val resp = prApiService.getRecordByTeams(param)
        return resp.data?: TeamSummaryDto()
    }

    override suspend fun getRecordDetail(param: TeamDetailParam): PrTeamDetail {
        val resp = prApiService.getRecordDetail(param)
        return resp.data?: TeamDetailDto()
    }

    override suspend fun getPagingHistories(
        param: HistoriesParam,
        page: Int,
        size: Int
    ): PagingResponse<HistoriesResponse> {

        return prApiService.getPagingHistories(param, page, size)
    }

    override suspend fun delHistory(param: HistoryDelParam): PrDelHistory {

        val resp = prApiService.delHistory(param)
        return resp.data ?: HistoryDelDto()
    }

    override suspend fun getDayGame(param: GameParam): PrGame {

        val resp = prApiService.getDayPlay(param)
        return resp.data ?: GameDto()
    }

    override suspend fun postNewGame(param: GameSaveParam): PrGameSave {

        val resp = prApiService.saveNewGame(param)
        return resp.data ?: GameSaveDto()
    }

    override suspend fun delUser(param: UserDelParam): PrDelUser {

        val resp = prApiService.delUser(param)
        return resp.data ?: UserDelDto()
    }

    override suspend fun postUser(param: UserRegisterParam): PrUserRegister {

        val resp = prApiService.postUser(param)
        return resp.data ?: UserRegisterDto()
    }
}