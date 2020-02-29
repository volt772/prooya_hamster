package com.apx5.apx5.remote

/**
 * Response - 팀기록 요약
 * @desc TAB 2
 */

data class RemoteTeamSummary (
    val win: Int,
    val draw: Int,
    val lose: Int,
    val year: Int
)