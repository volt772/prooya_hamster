package com.apx5.apx5.network.response

import com.apx5.apx5.constants.PrNetworks
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

/**
 * PrResponse
 * @Param E
 * @desc 데이터 Response
 */

data class MkPagingResponse<T> (
    @field:Json(name = "games") val games: List<T>
)