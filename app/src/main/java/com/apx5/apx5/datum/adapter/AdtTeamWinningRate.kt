package com.apx5.apx5.datum.adapter

import com.apx5.apx5.constants.PrTeam

/**
 * AdtGames
 * @desc 게임리스트 Adapter
 */

data class AdtTeamPerc (
    val team: PrTeam,
    val winningRate: Int
)