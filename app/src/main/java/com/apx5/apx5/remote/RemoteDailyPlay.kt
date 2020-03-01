package com.apx5.apx5.remote

/**
 * Response
 * @desc Tab : 4
 * @desc 일일경기
 */

data class RemoteDailyPlay (
    val awayscore: Int,
    val awayteam: String,
    val homescore: Int,
    val hometeam: String,
    val id: Int,
    val playdate: Int,
    val stadium: String,
    val starttime: Int
)