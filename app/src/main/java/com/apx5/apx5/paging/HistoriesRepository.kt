package com.apx5.apx5.paging

import androidx.paging.PagingData
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.paging.datum.Histories
import kotlinx.coroutines.flow.Flow

/**
 * HistoriesRepository
 */
interface HistoriesRepository {

    fun getHistories(ptPostTeams: PtPostTeams): Flow<PagingData<Histories>>
}