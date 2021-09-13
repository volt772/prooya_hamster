package com.apx5.domain.dto

/**
 * Histories
 * @desc 전체 경기목록
 */

data class Histories (
    val awayScore: Int,
    val awayTeam: String,
    val homeScore: Int,
    val homeTeam: String,
    val playDate: Int,
    val playId: Int,
    val playResult: String,
    val playSeason: Int,
    val playVs: String,
    val stadium: String
)