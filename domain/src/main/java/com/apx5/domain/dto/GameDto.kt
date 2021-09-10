package com.apx5.domain.dto

import com.apx5.apx5.datum.ops.OpsDailyPlay

/**
 * 오늘 내팀 경기목록
 */

data class GameDto(
    val games: List<OpsDailyPlay> = emptyList()
)
