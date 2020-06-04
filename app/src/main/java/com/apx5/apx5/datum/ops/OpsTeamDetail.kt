package com.apx5.apx5.datum.ops

/**
 * Response
 * @desc Tab : 2
 * @desc 팀상세전적
 */

data class OpsTeamDetail (
    val playDate: String,
    val playResult: String,
    val playVs: String,
    val ptGet: Int,
    val ptLost: Int
)
