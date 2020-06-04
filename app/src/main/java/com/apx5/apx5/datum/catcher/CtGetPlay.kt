package com.apx5.apx5.datum.catcher

import com.apx5.apx5.datum.ops.OpsDailyPlay
import com.google.gson.annotations.SerializedName

/**
 * 오늘 내팀 경기목록
 */

open class CtGetPlay {
    @SerializedName("games")
    val games: List<OpsDailyPlay> = emptyList()
}
