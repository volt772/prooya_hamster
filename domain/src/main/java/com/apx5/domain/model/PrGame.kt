package com.apx5.domain.model

import com.apx5.domain.ops.OpsDailyPlay

interface PrGame {

    val games: List<OpsDailyPlay>
}