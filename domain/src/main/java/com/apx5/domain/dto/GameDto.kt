package com.apx5.domain.dto

import com.apx5.domain.PrNetworkKeys
import com.apx5.domain.model.PrGame
import com.apx5.domain.ops.OpsDailyPlay
import com.google.gson.annotations.SerializedName

/**
 * GameDto
 * @desc 오늘 내팀 경기목록
 */

data class GameDto(

    @SerializedName(PrNetworkKeys.GAMES)
    val _games: List<OpsDailyPlay> = emptyList()

) : PrGame {

    override val games: List<OpsDailyPlay>
        get() = _games
}
