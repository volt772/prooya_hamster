package com.apx5.domain.ops

/**
 * Response
 * @desc Tab : 4
 * @desc 일일경기
 */

data class OpsDailyPlay (
    val awayscore: Int,
    val awayteam: String,
    val homescore: Int,
    val hometeam: String,
    val id: Int,
    val playdate: Int,
    val stadium: String,
    val starttime: Int,
    val registedId: Int
)