package com.apx5.apx5.network.dto

import com.apx5.apx5.remote.*
import com.google.gson.annotations.SerializedName

/**
 * 팀 간단데이터
 */

open class PrRecordsDto {
        @SerializedName("teams")
        val teams: List<RemoteTeamRecords> = emptyList()

        @SerializedName("summary")
        val summary: RemoteTeamSummary?= null
}