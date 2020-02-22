package com.apx5.apx5.datum

/**
 * DtDailyGame
 */

data class DtDailyGame(
    val gameId: Int,
    val awayScore: Int,
    val homeScore: Int,
    val awayTeam: String,
    val homeTeam: String,
    val playDate: Int,
    val startTime: Int,
    val stadium: String,
    val status: Int
)
