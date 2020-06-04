package com.apx5.apx5.datum.catcher

import com.apx5.apx5.datum.ops.OpsTeamDetail
import com.google.gson.annotations.SerializedName

/**
 * 팀 경기상세
 */

open class CtGetRecordDetail {
        @SerializedName("games")
        val games: List<OpsTeamDetail> = emptyList()
}