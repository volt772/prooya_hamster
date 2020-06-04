package com.apx5.apx5.datum.catcher

import com.apx5.apx5.datum.ops.OpsTeamRecords
import com.apx5.apx5.datum.ops.OpsTeamSummary
import com.google.gson.annotations.SerializedName

/**
 * 팀 간단데이터
 */

open class CtPostTeams {
        @SerializedName("teams")
        val teams: List<OpsTeamRecords> = emptyList()

        @SerializedName("summary")
        val summary: OpsTeamSummary?= null
}