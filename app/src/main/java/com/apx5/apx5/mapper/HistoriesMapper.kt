package com.apx5.apx5.mapper

import com.apx5.domain.dto.Histories
import com.apx5.domain.dto.HistoriesResponse
import com.apx5.domain.dto.HistoriesUi

/**
 * HistoriesMapper
 */

interface HistoriesMapper {

    suspend fun mapRemoteHistoriesListToDomain(remoteHistories: List<HistoriesResponse>): List<Histories>

    suspend fun mapRemoteHistoriesToDomain(remoteHistories: HistoriesResponse): Histories

    suspend fun mapDomainHistoriesListToUi(domainHistories: List<Histories>): List<HistoriesUi>

    suspend fun mapDomainHistoriesToUi(domainHistories: Histories): HistoriesUi
}