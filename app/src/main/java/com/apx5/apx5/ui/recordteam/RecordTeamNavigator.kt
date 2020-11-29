package com.apx5.apx5.ui.recordteam


import com.apx5.apx5.datum.DtTeamRecord
import com.apx5.apx5.datum.ops.OpsTeamDetail
import com.apx5.apx5.datum.ops.OpsTeamSummary

/**
 * RecordTeamNavigator
 */
interface RecordTeamNavigator {
    fun getDetailLists(year: Int, versus: String)
    fun showDetailLists(plays: List<OpsTeamDetail>)
    fun setTeamRecord(teams: List<DtTeamRecord>)
    fun setHeaderSummary(summary: OpsTeamSummary)
    fun cancelSpinKit()
}
