package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.apx5.apx5.datum.ops.OpsAllStatics
import com.apx5.apx5.datum.ops.OpsTeamWinningRate
import com.apx5.apx5.datum.ops.OpsUser
import com.google.gson.annotations.SerializedName

/**
 * 요약데이터
 */
data class StaticsResp(
    @SerializedName(PrNetworkKeys.USER)
    val user: OpsUser?= null,

    @SerializedName(PrNetworkKeys.ALL_STATICS)
    val allStatics: OpsAllStatics?= null,

    @SerializedName(PrNetworkKeys.TEAM_WINNING_RATE)
    val teamWinningRate: OpsTeamWinningRate?= null
)
