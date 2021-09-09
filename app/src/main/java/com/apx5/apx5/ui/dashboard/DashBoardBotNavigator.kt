package com.apx5.apx5.ui.dashboard

import com.apx5.apx5.R
import com.apx5.apx5.constants.PrTabMenu
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * DashBoardNav
 * @desc 탭메뉴
 */

class DashBoardBotNavigator {
    companion object {
        /**
         * Bottom Navigation
         * @param view BottomNavigationView
         * @param switchPage Func()
         */
        fun bottomNavLayout(
            view: BottomNavigationView,
            switchPage: (PrTabMenu) -> Unit
        ) {
            view.setOnItemSelectedListener { item ->
                item.isChecked = true
                val menuSelect = when (item.itemId) {
                    R.id.nav_statics -> PrTabMenu.STATICS
                    R.id.nav_team_record -> PrTabMenu.SEASON
                    R.id.nav_all_record -> PrTabMenu.RECORD_ALL
                    R.id.nav_daily_game -> PrTabMenu.DAYS
                    R.id.nav_setting -> PrTabMenu.SETTING
                    else -> PrTabMenu.STATICS
                }

                switchPage(menuSelect)
                false
            }
        }
    }
}