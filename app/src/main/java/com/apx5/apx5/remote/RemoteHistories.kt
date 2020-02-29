package com.apx5.apx5.remote

/**
 * Response - 기록전체
 * @desc TAB 3
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

