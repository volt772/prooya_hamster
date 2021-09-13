package com.apx5.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.apx5.data.data_source.HistoriesRemoteDataSource
import com.apx5.domain.dto.Histories
import com.apx5.domain.dto.HistoriesResponse
import com.apx5.domain.param.HistoriesParam
import com.apx5.domain.repository.PrHistories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * HistoriesRepositoryImpl
 */

class PrHistoriesImpl @Inject constructor(
    private val historiesDataSource: HistoriesRemoteDataSource
) : PrHistories {

    override fun getHistories(ptPostTeams: HistoriesParam): Flow<PagingData<Histories>> {
        return historiesDataSource.getHistories(ptPostTeams)
            .map { pagingData ->
                pagingData.map { remoteHistories ->
                    mapRemoteHistoriesToDomain(remoteHistories = remoteHistories)
                }
            }
    }

    private fun mapRemoteHistoriesToDomain(
        remoteHistories: HistoriesResponse
    ): Histories {
        return Histories(
            awayScore = remoteHistories.awayScore,
            awayTeam = remoteHistories.awayTeam,
            homeScore = remoteHistories.homeScore,
            homeTeam = remoteHistories.homeTeam,
            playDate = remoteHistories.playDate,
            playId = remoteHistories.playId,
            playResult = remoteHistories.playResult,
            playSeason = remoteHistories.playSeason,
            playVs = remoteHistories.playVs,
            stadium = remoteHistories.stadium
        )
    }
}