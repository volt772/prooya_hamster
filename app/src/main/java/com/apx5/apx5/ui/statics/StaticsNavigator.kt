package com.apx5.apx5.ui.statics


import com.apx5.apx5.db.entity.PrPlayEntity

/**
 * StaticsNavigator
 */
interface StaticsNavigator {
    fun showRecentPlayList(playList: List<PrPlayEntity>)
    fun cancelSpinKit()
}
