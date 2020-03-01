package com.apx5.apx5.remote

/**
 * Response
 * @desc Tab : 1
 * @desc 통계데이터 > 최근경기
 */

data class RemoteRecentPlay (
    val playDate: String,
    val playId: Int,
    val playResult: String,
    val playSeason: Int,
    val playVs: String,
    val playMyTeam: String,
    val ptGet: Int,
    val ptLost: Int
)