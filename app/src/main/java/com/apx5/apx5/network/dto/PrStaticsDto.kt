package com.apx5.apx5.network.dto

import com.apx5.apx5.remote.RemoteAllStatics
import com.apx5.apx5.remote.RemoteRecentPlay
import com.apx5.apx5.remote.RemoteSeasonStatics
import com.google.gson.annotations.SerializedName

/**
 * 요약데이터
 */

open class PrStaticsDto {
        @SerializedName("team")
        val team: String = ""

        @SerializedName("allStatics")
        val allStatics: RemoteAllStatics?= null

        @SerializedName("seasonStatics")
        val seasonStatics: RemoteSeasonStatics?= null

        @SerializedName("recentPlays")
        val recentPlays: List<RemoteRecentPlay>?= null
}
