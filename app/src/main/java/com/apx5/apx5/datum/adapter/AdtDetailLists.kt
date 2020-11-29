package com.apx5.apx5.datum.adapter

import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.constants.PrTeam

/**
 * Adapter Data Class
 * @desc Tab : 2
 * @desc 팀별 기록 선택 시, 상세내역
 */

data class AdtDetailLists (
    val awayScore: Int,
    val awayEmblem: PrTeam,
    val homeScore: Int,
    val homeEmblem: PrTeam,
    val playResult: PrResultCode,
    val playDate: String,
    val stadium: String
)