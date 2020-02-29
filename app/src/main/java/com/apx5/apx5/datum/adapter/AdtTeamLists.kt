package com.apx5.apx5.datum.adapter

/**
 * Adapter Data Class
 * @desc Tab : 2
 * @desc 팀별 기록 리스트
 */

data class AdtTeamLists(
    val year: Int,
    val team: String,
    val win: Int,
    val draw: Int,
    val lose: Int,
    val rate: Int,
    val teamEmblem: Int
)
