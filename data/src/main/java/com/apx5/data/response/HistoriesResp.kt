package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.squareup.moshi.Json

/**
 * HistoriesResponse
 * @desc 전체목록 (Paging)
 */

data class HistoriesResp (
    @field:Json(name = PrNetworkKeys.AWAY_SCORE) val awayScore: Int,
    @field:Json(name = PrNetworkKeys.AWAY_TEAM) val awayTeam: String,
    @field:Json(name = PrNetworkKeys.HOME_SCORE) val homeScore: Int,
    @field:Json(name = PrNetworkKeys.HOME_TEAM) val homeTeam: String,
    @field:Json(name = PrNetworkKeys.PLAY_DATE) val playDate: Int,
    @field:Json(name = PrNetworkKeys.PLAY_ID) val playId: Int,
    @field:Json(name = PrNetworkKeys.PLAY_RESULT) val playResult: String,
    @field:Json(name = PrNetworkKeys.PLAY_SEASON) val playSeason: Int,
    @field:Json(name = PrNetworkKeys.PLAY_VS) val playVs: String,
    @field:Json(name = PrNetworkKeys.STADIUM) val stadium: String
)