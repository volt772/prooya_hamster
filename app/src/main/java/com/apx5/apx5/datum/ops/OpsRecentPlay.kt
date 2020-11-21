package com.apx5.apx5.datum.ops

/**
 * Response
 * @desc Tab : 1
 * @desc 통계데이터 > 최근경기
 */

data class OpsRecentPlay (
    val awayScore: Int,
    val awayTeam: String,
    val homeScore: Int,
    val homeTeam: String,
    val playDate: Int,
    val playId: Int,
    val playResult: String,
    val playSeason: Int,
    val playVs: String,
    val stadium: String
)