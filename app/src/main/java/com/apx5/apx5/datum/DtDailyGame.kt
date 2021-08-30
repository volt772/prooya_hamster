package com.apx5.apx5.datum

import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam

/**
 * Data Class
 * @desc Tab : 4
 * @desc 일별데이터
 */

data class DtDailyGame(
    val gameId: Int,
    val awayScore: Int,
    val homeScore: Int,
    val awayTeam: PrTeam,
    val homeTeam: PrTeam,
    val playDate: Int,
    val startTime: String,
    val stadium: PrStadium,
    val status: PrGameStatus,
    val additionalInfo: String,
    val registeredGame: Boolean
)
