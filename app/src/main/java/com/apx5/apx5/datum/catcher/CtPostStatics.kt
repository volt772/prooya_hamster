package com.apx5.apx5.datum.catcher

import com.apx5.apx5.constants.PrNetworkKeys
import com.apx5.apx5.datum.ops.OpsAllStatics
import com.apx5.apx5.datum.ops.OpsRecentPlay
import com.apx5.apx5.datum.ops.OpsSeasonStatics
import com.google.gson.annotations.SerializedName

/**
 * 요약데이터
 */

open class CtPostStatics {
        @SerializedName(PrNetworkKeys.TEAM)
        val team: String = ""

        @SerializedName(PrNetworkKeys.ALL_STATICS)
        val allStatics: OpsAllStatics?= null

        @SerializedName(PrNetworkKeys.SEASON_STATICS)
        val seasonStatics: OpsSeasonStatics?= null

        @SerializedName(PrNetworkKeys.RECENT_PLAYS)
        val recentPlays: List<OpsRecentPlay>?= null
}
