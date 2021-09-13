package com.apx5.domain.dto

import com.squareup.moshi.Json

data class PagingResponse<T> (
    @field:Json(name = "games") val games: List<T>
)
