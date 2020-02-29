package com.apx5.apx5.remote

/**
 * Response - 팀상세 전적
 * @desc TAB 2
 */

data class RemoteTeamDetail (
    val playDate: String,
    val playResult: String,
    val playVs: String,
    val ptGet: Int,
    val ptLost: Int
)
