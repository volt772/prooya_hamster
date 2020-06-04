package com.apx5.apx5.datum.catcher

import com.apx5.apx5.datum.ops.OpsAllStatics
import com.apx5.apx5.datum.ops.OpsRecentPlay
import com.apx5.apx5.datum.ops.OpsSeasonStatics
import com.google.gson.annotations.SerializedName

/**
 * 요약데이터
 */

open class CtPostStatics {
        @SerializedName("team")
        val team: String = ""

        @SerializedName("allStatics")
        val allStatics: OpsAllStatics?= null

        @SerializedName("seasonStatics")
        val seasonStatics: OpsSeasonStatics?= null

        @SerializedName("recentPlays")
        val recentPlays: List<OpsRecentPlay>?= null
}
