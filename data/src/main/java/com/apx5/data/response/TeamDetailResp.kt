package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.apx5.domain.ops.OpsTeamDetail
import com.google.gson.annotations.SerializedName

/**
 * TeamDetailResp
 * @desc 팀 경기상세
 */

data class TeamDetailResp(
    @SerializedName(PrNetworkKeys.GAMES)
    val games: List<OpsTeamDetail> = emptyList()
)
