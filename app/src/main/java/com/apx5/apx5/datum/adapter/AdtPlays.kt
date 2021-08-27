package com.apx5.apx5.datum.adapter

import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.constants.PrTeam

/**
 * AdtGames
 * @desc 게임리스트 Adapter
 */

data class AdtPlays (
    val awayScore: Int,
    val awayTeam: String?= "",
    val awayEmblem: PrTeam,
    val homeScore: Int,
    val homeTeam: String?= "",
    val homeEmblem: PrTeam,
    val playDate: String,
    val playId: Int?= 0,
    val playResult: PrResultCode,
    val playSeason: Int?= 0,
    val playVersus: String?= "",
    val stadium: String
)