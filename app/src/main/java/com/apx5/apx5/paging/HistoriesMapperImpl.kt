package com.apx5.apx5.paging

import com.apx5.apx5.di.DefaultDispatcher
import com.apx5.apx5.paging.datum.Histories
import com.apx5.apx5.paging.datum.HistoriesResponse
import com.apx5.apx5.paging.datum.HistoriesUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
 * Created by Christopher Elias on 26/04/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class HistoriesMapperImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
//    private val defaultDispatcher: CoroutineDispatcher
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