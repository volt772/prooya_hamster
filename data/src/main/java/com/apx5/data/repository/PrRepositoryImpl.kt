package com.apx5.data.repository

import com.apx5.data.network.PrApiService2
import com.apx5.data.response.HistoriesResp
import com.apx5.domain.dto.*
import com.apx5.domain.param.*
import com.apx5.domain.repository.PrRepository2
import javax.inject.Inject

class PrRepositoryImpl @Inject constructor(private val prApiService: PrApiService2): PrRepository2 {
    override suspend fun getServerStatus(): ServerStatusDto {
        val resp = prApiService.getServerStatus()
        return ServerStatusDto(resp.data?.status?: 0)
    }

    override suspend fun getStatics(param: StaticsParam): StaticsDto {
        val resp = prApiService.getStatics(param)
        return resp.data?.let {
            StaticsDto(
                user = it.user,
                allStatics = it.allStatics,
                teamWinningRate = it.teamWinningRate
            )
        } ?: StaticsDto()
    }

    override suspend fun getRecordByTeams(param: TeamSummaryParam): TeamSummaryDto {
        val resp = prApiService.getRecordByTeams(param)
        return resp.data?.let {
            TeamSummaryDto(
                teams = it.teams,
                summary = it.summary
            )
        } ?: TeamSummaryDto()
    }

    override suspend fun getRecordDetail(param: TeamDetailParam): TeamDetailDto {
        val resp = prApiService.getRecordDetail(param)
        return resp.data?.let {
            TeamDetailDto(
                games = it.games
            )
        } ?: TeamDetailDto()
    }

    override suspend fun getHistories(param: HistoriesParam): HistoriesDto {
        val resp = prApiService.getHistories(param)
        return resp.data?.let {
            historiesToDomain(it)
        } ?: HistoriesDto()
    }

    override suspend fun getPagingHistories(param: HistoriesParam, page: Int, size: Int): PagingResponse<HistoriesResponse> {
        return prApiService.getPagingHistories(param, page, size)
    }

    override suspend fun delHistory(param: HistoryDelParam): HistoryDelDto {
        val resp = prApiService.delHistory(param)
        return resp.data?.let {
            HistoryDelDto(
                count = it.count
            )
        } ?: HistoryDelDto()
    }

    override suspend fun getDayGame(param: GameParam): GameDto {
        val resp = prApiService.getDayPlay(param)
        return resp.data?.let {
            GameDto(
                games = it.games
            )
        } ?: GameDto()
    }

    override suspend fun postNewGame(param: GameSaveParam): GameSaveDto {
        val resp = prApiService.saveNewGame(param)
        return resp.data?.let {
            GameSaveDto(
                result = it.result
            )
        } ?: GameSaveDto()
    }

    override suspend fun delUser(param: UserDelParam): UserDelDto {
        val resp = prApiService.delUser(param)
        return resp.data?.let {
            UserDelDto(
                count = it.count
            )
        } ?: UserDelDto()
    }

    override suspend fun postUser(param: UserRegisterParam): UserRegisterDto {
        val resp = prApiService.postUser(param)
        return resp.data?.let {
            UserRegisterDto(
                id = it.id,
                team = it.team
            )
        } ?: UserRegisterDto()
    }

    private fun historiesToDomain(history: HistoriesResp): HistoriesDto {
        return HistoriesDto(
            awayScore = history.awayScore,
            awayTeam = history.awayTeam,
            homeScore = history.homeScore,
            homeTeam = history.homeTeam,
            playDate = history.playDate,
            playId = history.playId,
            playResult = history.playResult,
            playSeason = history.playSeason,
            playVs = history.playVs,
            stadium = history.stadium
        )
    }
}