package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.apx5.domain.ops.OpsDailyPlay
import com.google.gson.annotations.SerializedName

/**
 * GameResp
 * @desc 오늘 내팀 경기목록
 */

data class GameResp(
    @SerializedName(PrNetworkKeys.GAMES)
    val games: List<OpsDailyPlay> = emptyList()
)