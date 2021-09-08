package com.apx5.apx5.paging

import androidx.paging.PagingData
import androidx.paging.map
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.paging.datum.Histories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * HistoriesRepositoryImpl
 */
class HistoriesRepositoryImpl @Inject constructor(
    private val historiesDataSource: HistoriesRemoteDataSource,
    private val mapper: HistoriesMapper
) : HistoriesRepository {

    override fun getHistories(ptPostTeams: PtPostTeams): Flow<PagingData<Histories>> {
        return historiesDataSource.getHistories(ptPostTeams)
            .map { pagingData ->
                pagingData.map { remoteHistories ->
                    mapper.mapRemoteHistoriesToDomain(remoteHistories = remoteHistories)
                }
            }
    }
}