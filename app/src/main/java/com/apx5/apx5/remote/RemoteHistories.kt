package com.apx5.apx5.remote

/**
 * Response
 * @desc Tab : 3
 * @desc 기록전체
 */

data class RemoteHistories (
    val playDate: String,
    val playId: Int,
    val playMyTeam: String,
    val playResult: String,
    val playSeason: Int,
    val playVs: String,
    val ptGet: Int,
    val ptLost: Int
)

