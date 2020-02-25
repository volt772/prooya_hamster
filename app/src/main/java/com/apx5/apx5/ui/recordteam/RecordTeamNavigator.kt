package com.apx5.apx5.ui.recordteam


import com.apx5.apx5.db.entity.PrTeamEntity
import com.apx5.apx5.remote.RemoteTeamDetail
import java.util.*

/**
 * RecordTeamNavigator
 */
interface RecordTeamNavigator {
    fun selectSeasonYear(year: Int)
    fun getDetailLists(year: String, versus: String)
    fun showDetailLists(plays: List<RemoteTeamDetail>)
    fun setTeamRecord(teams: List<PrTeamEntity>)
    fun setHeaderSummary(summary: HashMap<String, Int>)
    fun cancelSpinKit()
}
