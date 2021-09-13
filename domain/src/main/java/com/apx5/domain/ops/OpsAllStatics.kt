package com.apx5.domain.ops

/**
 * Response
 * @desc Tab : 1
 * @desc 통계데이터
 */

data class OpsAllStatics (
    val count: Int,
    val draw: Int,
    val lose: Int,
    val rate: Int,
    val win: Int
)