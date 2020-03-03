package com.apx5.apx5.ui.team

import com.apx5.apx5.datum.adapter.AdtTeamSelection

/**
 * TeamNavigator
 */
interface TeamNavigator {
    fun switchPageBySelectType()
    fun vectoredRestart()
    fun selectMyTeam(team: AdtTeamSelection)
}
