package com.apx5.domain.ops

/**
 * Response
 * @desc Tab : 2
 * @desc 팀요약
 */

data class OpsTeamSummary (
    val win: Int,
    val draw: Int,
    val lose: Int,
    val year: Int
)