package com.apx5.data.data_source

import androidx.paging.PagingData
import com.apx5.domain.dto.HistoriesResponse
import com.apx5.domain.param.HistoriesParam
import kotlinx.coroutines.flow.Flow

/**
 * HistoriesRemoteDataSource
 */
interface HistoriesRemoteDataSource {

    fun getHistories(ptPostTeams: HistoriesParam): Flow<PagingData<HistoriesResponse>>
}