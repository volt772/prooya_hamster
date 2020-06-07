package com.apx5.apx5.datum.catcher

import com.apx5.apx5.constants.PrNetworkKeys
import com.apx5.apx5.datum.ops.OpsTeamRecords
import com.apx5.apx5.datum.ops.OpsTeamSummary
import com.google.gson.annotations.SerializedName

/**
 * 팀 간단데이터
 */

open class CtPostTeams {
        @SerializedName(PrNetworkKeys.TEAMS)
        val teams: List<OpsTeamRecords> = emptyList()

        @SerializedName(PrNetworkKeys.SUMMARY)
        val summary: OpsTeamSummary?= null
}