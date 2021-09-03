package com.apx5.apx5.paging

import com.apx5.apx5.paging.datum.Histories
import com.apx5.apx5.paging.datum.HistoriesResponse
import com.apx5.apx5.paging.datum.HistoriesUi

interface HistoriesMapper {

    suspend fun mapRemoteHistoriesListToDomain(remoteHistories: List<HistoriesResponse>): List<Histories>

    suspend fun mapRemoteHistoriesToDomain(remoteHistories: HistoriesResponse): Histories

    suspend fun mapDomainHistoriesListToUi(domainHistories: List<Histories>): List<HistoriesUi>

    suspend fun mapDomainHistoriesToUi(domainHistories: Histories): HistoriesUi
}