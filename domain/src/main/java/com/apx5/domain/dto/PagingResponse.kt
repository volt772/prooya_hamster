package com.apx5.domain.dto

import com.squareup.moshi.Json

/**
 * PagingResponse
 * @desc Response Paging
 */
data class PagingResponse<T> (
    @field:Json(name = "games") val games: List<T>
)
