package com.apx5.apx5.datum

/**
 * Data Class
 * @desc Tab : 1
 * @desc 최근경기리스트
 */

data class DtPlays (
    val playId: Int,
    val playPtGet: Int,
    val playPtLost: Int,
    val playSeason: Int,
    val playDate: String,
    val playResult: String,
    val playVersus: String,
    val playMyTeam: String
)