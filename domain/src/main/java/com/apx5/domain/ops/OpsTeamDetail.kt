package com.apx5.domain.ops

/**
 * Response
 * @desc Tab : 2
 * @desc 팀상세전적
 */

data class OpsTeamDetail (
    val playResult: String,
    val playVs: String,
    val playDate: Int,
    val awayTeam: String,
    val awayScore: Int,
    val homeTeam: String,
    val homeScore: Int,
    val stadium: String
)
