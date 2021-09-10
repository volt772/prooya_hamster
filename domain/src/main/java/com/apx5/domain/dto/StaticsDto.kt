package com.apx5.domain.dto

import com.apx5.apx5.datum.ops.OpsAllStatics
import com.apx5.apx5.datum.ops.OpsTeamWinningRate
import com.apx5.apx5.datum.ops.OpsUser

/**
 * 요약데이터
 */
data class StaticsDto(
    val user: OpsUser?= null,

    val allStatics: OpsAllStatics?= null,

    val teamWinningRate: OpsTeamWinningRate?= null
)
