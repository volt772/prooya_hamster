package com.apx5.apx5.datum.adapter

import com.apx5.apx5.constants.PrTeam

/**
 * Adapter Data Class
 * @desc Tab : 1, 3
 * @desc 최근경기 리스트, 전체기록 리스트
 */

data class AdtPlayLists (
    val awayScore: Int,
    val awayTeam: String,
    val awayEmblem: PrTeam,
    val homeScore: Int,
    val homeTeam: String,
    val homeEmblem: PrTeam,
    val playDate: String,
    val playId: Int,
    val playResult: String,
    val playSeason: Int,
    val playVersus: String,
    val stadium: String
)
