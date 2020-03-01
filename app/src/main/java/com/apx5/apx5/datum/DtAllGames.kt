package com.apx5.apx5.datum

/**
 * Data Class
 * @desc Tab : 3
 * @desc 전체기록리스트
 */

data class DtAllGames (
    val playId: Int,
    val playPtGet: Int,
    val playPtLost: Int,
    val playSeason: Int,
    val playDate: String,
    val playResult: String,
    val playVersus: String,
    val playMyTeam: String
)