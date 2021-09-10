package com.apx5.domain.dto

import com.squareup.moshi.Json

/**
 * HistoriesResponse
 */
data class HistoriesPagingDto (
    val games: List<HistoriesDto>
)