package com.apx5.domain.repository

import androidx.paging.PagingData
import com.apx5.domain.dto.Histories
import com.apx5.domain.param.HistoriesParam
import kotlinx.coroutines.flow.Flow

/**
 * PrHistories
 */

interface PrHistories {

    fun getHistories(ptPostTeams: HistoriesParam): Flow<PagingData<Histories>>
}