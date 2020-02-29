package com.apx5.apx5.remote

/**
 * Response - 팀기록
 * @desc TAB 2
 */

data class RemoteTeamRecords(
    val win: Int,
    val draw: Int,
    val lose: Int,
    val rate: Int,
    val team: String,
    val year: Int
)