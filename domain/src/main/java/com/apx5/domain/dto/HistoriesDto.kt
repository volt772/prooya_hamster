package com.apx5.domain.dto

import com.squareup.moshi.Json

/**
 * HistoriesResponse
 */
data class HistoriesDto (
    val awayScore: Int?= 0,
    val awayTeam: String?= "",
    val homeScore: Int?= 0,
    val homeTeam: String?= "",
    val playDate: Int?= 0,
    val playId: Int?= 0,
    val playResult: String?= "",
    val playSeason: Int?= 0,
    val playVs: String?= "",
    val stadium: String?= ""
)
