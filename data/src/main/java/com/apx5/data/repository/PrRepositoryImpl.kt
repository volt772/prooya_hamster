package com.apx5.data.repository

import com.apx5.apx5.datum.ops.OpsTeamSummary
import com.apx5.data.network.PrApiService2
import com.apx5.domain.dto.*
import com.apx5.domain.param.*
import com.apx5.domain.repository.PrRepository2
import kotlinx.coroutines.withContext
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
            HistoriesDto(
                awayScore = it.awayScore,
                awayTeam = it.awayTeam,
                homeScore = it.homeScore,
                homeTeam = it.homeTeam,
                playDate = it.playDate,
                playId = it.playId,
                playResult = it.playResult,
                playSeason = it.playSeason,
                playVs = it.playVs,
                stadium = it.stadium
            )
        } ?: HistoriesDto()
    }

    override suspend fun getPagingHistories(param: HistoriesParam, page: Int, size: Int): HistoriesPagingDto {
        val resp = prApiService.getPagingHistories(param, page, size)

        return HistoriesPagingDto(
            games = mutableListOf<HistoriesDto>().also { list ->
                resp.games.forEach{
                    list.add(
                        HistoriesDto(
                            awayScore = it.awayScore,
                            awayTeam = it.awayTeam,
                            homeScore = it.homeScore,
                            homeTeam = it.homeTeam,
                            playDate = it.playDate,
                            playId = it.playId,
                            playResult = it.playResult,
                            playSeason = it.playSeason,
                            playVs = it.playVs,
                            stadium = it.stadium
                        )
                    )
                }
            }
        )
    }

    override suspend fun delHistory(param: HistoryDelParam): HistoryDelDto {
        TODO("Not yet implemented")
    }

    override suspend fun getDayPlay(param: GameParam): GameDto {
        TODO("Not yet implemented")
    }

    override suspend fun postNewGame(param: GameSaveParam): GameSaveDto {
        TODO("Not yet implemented")
    }

    override suspend fun delUser(param: UserDelParam): UserDelDto {
        TODO("Not yet implemented")
    }

    override suspend fun postUser(param: UserRegisterParam): UserRegisterDto {
        TODO("Not yet implemented")
    }
}