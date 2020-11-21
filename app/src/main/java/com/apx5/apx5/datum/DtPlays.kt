package com.apx5.apx5.datum

/**
 * Data Class
 * @desc Tab : 1
 * @desc 최근경기리스트
 */

data class DtPlays (
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