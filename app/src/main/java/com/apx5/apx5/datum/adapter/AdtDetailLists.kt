package com.apx5.apx5.datum.adapter

/**
 * Adapter Data Class
 * @desc Tab : 2
 * @desc 팀별 기록 선택 시, 상세내역
 */

data class AdtDetailLists (
    var emblemTeam: Int,
    var ptGet: Int,
    var ptLost: Int,
    var playDate: String,
    var playResult: String,
    var playVs: String
)
