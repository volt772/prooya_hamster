package com.apx5.apx5.ui.recordteam

import com.apx5.apx5.datum.ops.OpsTeamDetail

/**
 * RecordTeamNavigator
 */
interface RecordTeamNavigator {
    fun getDetailLists(year: Int, versus: String)
    fun showDetailLists(games: List<OpsTeamDetail>)
}
