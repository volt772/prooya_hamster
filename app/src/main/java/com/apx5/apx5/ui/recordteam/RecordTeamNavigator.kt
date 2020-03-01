package com.apx5.apx5.ui.recordteam


import com.apx5.apx5.datum.DtTeamRecord
import com.apx5.apx5.remote.RemoteTeamDetail
import com.apx5.apx5.remote.RemoteTeamSummary

/**
 * RecordTeamNavigator
 */
interface RecordTeamNavigator {
    fun selectSeasonYear(year: Int)
    fun getDetailLists(year: Int, versus: String)
    fun showDetailLists(plays: List<RemoteTeamDetail>)
    fun setTeamRecord(teams: List<DtTeamRecord>)
    fun setHeaderSummary(summary: RemoteTeamSummary)
    fun cancelSpinKit()
}
