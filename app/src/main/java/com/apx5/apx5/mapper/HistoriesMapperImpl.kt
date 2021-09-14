package com.apx5.apx5.mapper

import com.apx5.apx5.di.DefaultDispatcher
import com.apx5.domain.dto.Histories
import com.apx5.domain.dto.HistoriesResponse
import com.apx5.domain.dto.HistoriesUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * HistoriesMapperImpl
 */

class HistoriesMapperImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : HistoriesMapper {

    override suspend fun mapRemoteHistoriesListToDomain(
        remoteHistories: List<HistoriesResponse>
    ): List<Histories> {
        return withContext(defaultDispatcher) {
            remoteHistories.map {
                mapRemoteHistoriesToDomain(it)
            }
        }
    }

    override suspend fun mapRemoteHistoriesToDomain(
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

    override suspend fun mapDomainHistoriesListToUi(
        domainHistories: List<Histories>
    ): List<HistoriesUi> {
        return withContext(defaultDispatcher) {
            domainHistories.map {
                mapDomainHistoriesToUi(it)
            }
        }
    }

    override suspend fun mapDomainHistoriesToUi(
        domainHistories: Histories
    ): HistoriesUi {
        return HistoriesUi(
            awayScore = domainHistories.awayScore,
            awayTeam = domainHistories.awayTeam,
            homeScore = domainHistories.homeScore,
            homeTeam = domainHistories.homeTeam,
            playDate = domainHistories.playDate,
            playId = domainHistories.playId,
            playResult = domainHistories.playResult,
            playSeason = domainHistories.playSeason,
            playVs = domainHistories.playVs,
            stadium = domainHistories.stadium
        )
    }
}