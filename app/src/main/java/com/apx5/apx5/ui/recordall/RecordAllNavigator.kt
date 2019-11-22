package com.apx5.apx5.ui.recordall


import com.apx5.apx5.db.entity.PrPlayEntity

/**
 * RecordAllNavigator
 */
interface RecordAllNavigator {
    fun selectYear(year: Int)
    fun setHistory(plays: List<PrPlayEntity>, year: Int)
    fun delHistoryItem(playId: String, playSeason: String, playVersus: String, playResult: String)
}
