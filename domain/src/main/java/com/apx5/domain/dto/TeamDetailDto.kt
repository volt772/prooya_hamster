package com.apx5.domain.dto

import com.apx5.domain.ops.OpsTeamDetail

/**
 * TeamDetailDto
 * @desc 팀 경기상세
 */

data class TeamDetailDto(
    val games: List<OpsTeamDetail> = emptyList()
)
