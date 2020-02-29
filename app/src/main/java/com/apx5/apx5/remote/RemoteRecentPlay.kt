package com.apx5.apx5.remote

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