package com.apx5.apx5.ui.statics


import com.apx5.apx5.datum.ops.OpsDailyPlay

/**
 * StaticsNavigator
 */
interface StaticsNavigator {
    fun showTodayGame(games: List<OpsDailyPlay>)
    fun saveMyTeamCode(teamCode: String)
    fun cancelSpinKit()
    fun showSuccessDialog()
}
