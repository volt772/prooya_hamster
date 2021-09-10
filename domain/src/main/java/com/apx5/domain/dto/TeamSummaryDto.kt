package com.apx5.domain.dto

import com.apx5.apx5.datum.ops.OpsTeamRecords
import com.apx5.apx5.datum.ops.OpsTeamSummary

/**
 * 팀 간단데이터
 */

data class TeamSummaryDto(
    val teams: List<OpsTeamRecords> = emptyList(),
    val summary: OpsTeamSummary?= null
)