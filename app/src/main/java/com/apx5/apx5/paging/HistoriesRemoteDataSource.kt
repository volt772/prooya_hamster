package com.apx5.apx5.paging

import androidx.paging.PagingData
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.paging.datum.HistoriesResponse
import kotlinx.coroutines.flow.Flow

/**
 * HistoriesRemoteDataSource
 */
interface HistoriesRemoteDataSource {

    fun getHistories(ptPostTeams: PtPostTeams): Flow<PagingData<HistoriesResponse>>
}