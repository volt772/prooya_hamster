package com.apx5.apx5.ui.statics


import com.apx5.apx5.datum.DtPlays

/**
 * StaticsNavigator
 */
interface StaticsNavigator {
    fun showRecentPlayList(playList: List<DtPlays>)
    fun cancelSpinKit()
}
