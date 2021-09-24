package com.apx5.domain.model

import com.apx5.domain.ops.OpsTeamRecords
import com.apx5.domain.ops.OpsTeamSummary

interface PrTeamSummary {

    val teams: List<OpsTeamRecords>

    val summary: OpsTeamSummary?
}