package com.apx5.apx5.datum

/**
 * Data Class
 * @desc Tab : 2
 * @desc 팀별 상세데이터
 */

data class DtTeamRecord (
    val year: Int,
    val team: String,
    val win: Int,
    val lose: Int,
    val draw: Int,
    val rate: Int
)