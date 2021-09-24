package com.apx5.domain.model

import com.apx5.domain.ops.OpsAllStatics
import com.apx5.domain.ops.OpsTeamWinningRate
import com.apx5.domain.ops.OpsUser

interface PrStatics {

    val user: OpsUser?

    val allStatics: OpsAllStatics?

    val teamWinningRate: OpsTeamWinningRate?
}