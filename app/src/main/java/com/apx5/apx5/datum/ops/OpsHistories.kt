package com.apx5.apx5.datum.ops

/**
 * Response
 * @desc Tab : 3
 * @desc 기록전체
 */

data class OpsHistories (
    val playDate: String,
    val playId: Int,
    val playMyTeam: String,
    val playResult: String,
    val playSeason: Int,
    val playVs: String,
    val ptGet: Int,
    val ptLost: Int
)

