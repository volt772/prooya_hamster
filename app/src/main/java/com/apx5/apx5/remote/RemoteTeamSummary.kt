package com.apx5.apx5.remote

/**
 * Response
 * @desc Tab : 2
 * @desc 팀요약
 */

data class RemoteTeamSummary (
    val win: Int,
    val draw: Int,
    val lose: Int,
    val year: Int
)