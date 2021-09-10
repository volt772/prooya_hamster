package com.apx5.data.response

import com.squareup.moshi.Json

/**
 * PrPagingResponse
 * @Param E
 * @desc 데이터 Response
 */

data class PrPagingResponse<T> (
    @field:Json(name = "games") val games: List<T>
)