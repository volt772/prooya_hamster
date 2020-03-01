package com.apx5.apx5.remote

/**
 * Response
 * @desc Tab : 2
 * @desc 팀상세전적
 */

data class RemoteTeamDetail (
    val playDate: String,
    val playResult: String,
    val playVs: String,
    val ptGet: Int,
    val ptLost: Int
)
