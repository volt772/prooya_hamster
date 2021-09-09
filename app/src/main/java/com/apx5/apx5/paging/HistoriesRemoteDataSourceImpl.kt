package com.apx5.apx5.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.paging.datum.HistoriesResponse
import com.apx5.apx5.repository.PrRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * HistoriesRemoteDataSourceImpl
 */

const val NETWORK_PAGE_SIZE = 20

class HistoriesRemoteDataSourceImpl @Inject constructor(
    private val prRepository: PrRepository,
) : HistoriesRemoteDataSource {

    override fun getHistories(ptPostTeams: PtPostTeams): Flow<PagingData<HistoriesResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                HistoriesPagingSource(prRepository = prRepository, ptPostTeams = ptPostTeams)
            }
        ).flow
    }
}