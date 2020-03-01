package com.apx5.apx5.remote

/**
 * Response
 * @desc Tab : 2
 * @desc 팀기록
 */

data class RemoteTeamRecords(
    val win: Int,
    val draw: Int,
    val lose: Int,
    val rate: Int,
    val team: String,
    val year: Int
)