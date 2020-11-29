package com.apx5.apx5.datum

/**
 * Data Class
 * @desc Tab : 3
 * @desc 전체기록리스트
 */

data class DtAllGames (
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