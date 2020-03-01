package com.apx5.apx5.datum.adapter

/**
 * Adapter Data Class
 * @desc Tab : 1, 3
 * @desc 최근경기 리스트, 전체기록 리스트
 */

data class AdtPlayLists (
    val playVersus: String,
    val playId: Int,
    val playSeason: Int,
    val playResult: String,
    val playDate: String,
    val scoreMy: Int,
    val scoreVs: Int,
    val emblemMy: Int,
    val emblemVs: Int
)
