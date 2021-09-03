package com.apx5.apx5.paging

import androidx.paging.PagingData
import androidx.paging.map
import com.apx5.apx5.paging.datum.Histories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/*
 * Created by Christopher Elias on 26/04/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class HistoriesRepositoryImpl @Inject constructor(
    private val historiesDataSource: HistoriesRemoteDataSource,
    private val mapper: HistoriesMapper
) : HistoriesRepository {

    override fun getHistories(): Flow<PagingData<Histories>> {
        println("probe : HistoriesRepositoryImpl")
        return historiesDataSource.getHistories()
            .map { pagingData ->
                pagingData.map { remoteHistories ->
                    mapper.mapRemoteHistoriesToDomain(remoteHistories = remoteHistories)
                }
            }
    }
}