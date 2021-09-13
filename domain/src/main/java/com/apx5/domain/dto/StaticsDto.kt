package com.apx5.domain.dto

import com.apx5.domain.ops.OpsAllStatics
import com.apx5.domain.ops.OpsTeamWinningRate
import com.apx5.domain.ops.OpsUser

/**
 * StaticsDto
 * @desc 요약데이터
 */
data class StaticsDto(
    val user: OpsUser?= null,

    val allStatics: OpsAllStatics?= null,

    val teamWinningRate: OpsTeamWinningRate?= null
)
