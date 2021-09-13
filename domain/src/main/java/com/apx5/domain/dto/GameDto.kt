package com.apx5.domain.dto

import com.apx5.domain.ops.OpsDailyPlay

/**
 * GameDto
 * @desc 오늘 내팀 경기목록
 */

data class GameDto(
    val games: List<OpsDailyPlay> = emptyList()
)
