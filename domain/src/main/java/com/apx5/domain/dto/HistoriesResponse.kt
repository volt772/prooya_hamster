package com.apx5.domain.dto

import com.squareup.moshi.Json

/**
 * HistoriesResponse
 * @desc 전체기록 Resp
 */

data class HistoriesResponse (
    @field:Json(name = "awayScore") val awayScore: Int,
    @field:Json(name = "awayTeam") val awayTeam: String,
    @field:Json(name = "homeScore") val homeScore: Int,
    @field:Json(name = "homeTeam") val homeTeam: String,
    @field:Json(name = "playDate") val playDate: Int,
    @field:Json(name = "playId") val playId: Int,
    @field:Json(name = "playResult") val playResult: String,
    @field:Json(name = "playSeason") val playSeason: Int,
    @field:Json(name = "playVs") val playVs: String,
    @field:Json(name = "stadium") val stadium: String
)