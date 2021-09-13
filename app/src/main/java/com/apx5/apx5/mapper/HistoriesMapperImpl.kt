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
        remoteHistories2: HistoriesResponse
    ): Histories {
        return Histories(
            awayScore = remoteHistories2.awayScore,
            awayTeam = remoteHistories2.awayTeam,
            homeScore = remoteHistories2.homeScore,
            homeTeam = remoteHistories2.homeTeam,
            playDate = remoteHistories2.playDate,
            playId = remoteHistories2.playId,
            playResult = remoteHistories2.playResult,
            playSeason = remoteHistories2.playSeason,
            playVs = remoteHistories2.playVs,
            stadium = remoteHistories2.stadium
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
        domainHistories2: Histories
    ): HistoriesUi {
        return HistoriesUi(
            awayScore = domainHistories2.awayScore,
            awayTeam = domainHistories2.awayTeam,
            homeScore = domainHistories2.homeScore,
            homeTeam = domainHistories2.homeTeam,
            playDate = domainHistories2.playDate,
            playId = domainHistories2.playId,
            playResult = domainHistories2.playResult,
            playSeason = domainHistories2.playSeason,
            playVs = domainHistories2.playVs,
            stadium = domainHistories2.stadium
        )
    }

}