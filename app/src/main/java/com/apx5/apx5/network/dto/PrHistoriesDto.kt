package com.apx5.apx5.network.dto

import com.apx5.apx5.remote.*
import com.google.gson.annotations.SerializedName

/**
 * 전체 간단데이터
 */

open class PrHistoriesDto {
        @SerializedName("games")
        val games: List<RemoteHistories> = emptyList()
}