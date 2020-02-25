package com.apx5.apx5.ui.recordall


import com.apx5.apx5.datum.DtAllGames
import com.apx5.apx5.datum.adapter.AdtPlayDelTarget

/**
 * RecordAllNavigator
 */
interface RecordAllNavigator {
    fun selectYear(year: Int)
    fun setHistory(plays: List<DtAllGames>, year: Int)
    fun delHistoryItem(delPlay: AdtPlayDelTarget)
    fun cancelSpinKit()
}
