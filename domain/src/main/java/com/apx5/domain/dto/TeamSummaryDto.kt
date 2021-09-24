package com.apx5.domain.dto

import com.apx5.domain.PrNetworkKeys
import com.apx5.domain.model.PrTeamSummary
import com.apx5.domain.ops.OpsTeamRecords
import com.apx5.domain.ops.OpsTeamSummary
import com.google.gson.annotations.SerializedName

/**
 * TeamSummaryDto
 * @desc 팀 간단데이터
 */

data class TeamSummaryDto(

    @SerializedName(PrNetworkKeys.TEAMS)
    val _teams: List<OpsTeamRecords> = emptyList(),

    @SerializedName(PrNetworkKeys.SUMMARY)
    val _summary: OpsTeamSummary?= null

) : PrTeamSummary {

    override val teams: List<OpsTeamRecords>
        get() = _teams

    override val summary: OpsTeamSummary?
        get() = _summary
}