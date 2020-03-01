package com.apx5.apx5.remote

/**
 * Response
 * @desc Tab : 1
 * @desc 통계데이터
 */

data class RemoteAllStatics (
    val count: Int,
    val draw: Int,
    val lose: Int,
    val rate: Int,
    val win: Int
)