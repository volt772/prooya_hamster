package com.apx5.apx5.datum.ops

/**
 * Response
 * @desc Tab : 3
 * @desc 기록전체
 */

data class OpsHistories (
    val awayScore: Int,
    val awayTeam: String,
    val homeScore: Int,
    val homeTeam: String,
    val playDate: Int,
    val playId: Int,
    val playResult: String,
    val playSeason: Int,
    val playVs: String
)

