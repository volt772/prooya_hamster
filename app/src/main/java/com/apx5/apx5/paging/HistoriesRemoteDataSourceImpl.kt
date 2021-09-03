package com.apx5.apx5.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apx5.apx5.paging.datum.HistoriesResponse
import com.apx5.apx5.repository.PrRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
 * Created by Christopher Elias on 26/04/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */


const val NETWORK_PAGE_SIZE = 25

class HistoriesRemoteDataSourceImpl @Inject constructor(
    private val prRepository: PrRepository,
) : HistoriesRemoteDataSource {

    override fun getHistories(): Flow<PagingData<HistoriesResponse>> {
//        Timber.d("Request movies")
        println("probe : HistoriesRemoteDataSourceImpl")
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                HistoriesPagingSource(prRepository = prRepository)
//                HistoriesPagingSource()
            }
        ).flow
    }
}