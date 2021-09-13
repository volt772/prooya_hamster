package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.apx5.domain.ops.OpsTeamRecords
import com.apx5.domain.ops.OpsTeamSummary
import com.google.gson.annotations.SerializedName

/**
 * TeamSummaryResp
 * @desc 팀 간단데이터
 */

data class TeamSummaryResp(
    @SerializedName(PrNetworkKeys.TEAMS)
    val teams: List<OpsTeamRecords> = emptyList(),

    @SerializedName(PrNetworkKeys.SUMMARY)
    val summary: OpsTeamSummary?= null
)