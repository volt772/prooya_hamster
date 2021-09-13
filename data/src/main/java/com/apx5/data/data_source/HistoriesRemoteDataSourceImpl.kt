package com.apx5.data.data_source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apx5.domain.dto.HistoriesResponse
import com.apx5.domain.param.HistoriesParam
import com.apx5.domain.repository.PrRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * HistoriesRemoteDataSourceImpl
 */

const val NETWORK_PAGE_SIZE = 20

class HistoriesRemoteDataSourceImpl @Inject constructor(
    private val prRepository: PrRepository,
) : HistoriesRemoteDataSource {

    override fun getHistories(ptPostTeams: HistoriesParam): Flow<PagingData<HistoriesResponse>> {
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