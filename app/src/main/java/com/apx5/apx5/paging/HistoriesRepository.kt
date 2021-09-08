package com.apx5.apx5.paging

import androidx.paging.PagingData
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.paging.datum.Histories
import kotlinx.coroutines.flow.Flow

/*
 * Created by Christopher Elias on 26/04/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

interface HistoriesRepository {

    fun getHistories(ptPostTeams: PtPostTeams): Flow<PagingData<Histories>>
}