package com.apx5.apx5.network.dto

import com.apx5.apx5.remote.*
import com.google.gson.annotations.SerializedName

/**
 * 팀 경기상세
 */

open class PrRecordDetailDto {
        @SerializedName("games")
        val games: List<RemoteTeamDetail> = emptyList()
}