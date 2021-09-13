package com.apx5.domain.ops

/**
 * Response
 * @desc Tab : 2
 * @desc 시즌기록
 */

data class OpsTeamRecords(
    val win: Int,
    val draw: Int,
    val lose: Int,
    val rate: Int,
    val team: String,
    val year: Int
)