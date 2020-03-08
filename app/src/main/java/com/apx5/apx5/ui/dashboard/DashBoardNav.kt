package com.apx5.apx5.ui.dashboard

import android.content.Context
import android.view.MenuItem
import com.apx5.apx5.R
import com.apx5.apx5.constants.PrTabMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

/**
 * DashBoardNav
 * @desc 탭메뉴
 */

class DashBoardNav {
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
            view.setOnNavigationItemSelectedListener { item ->
                item.isChecked = true
                val menuSelect = when (item.itemId) {
                    R.id.nav_statics -> PrTabMenu.STATICS
                    R.id.nav_team_record -> PrTabMenu.RECORD_TEAM
                    R.id.nav_all_record -> PrTabMenu.RECORD_ALL
                    R.id.nav_daily_game -> PrTabMenu.DAYS
                    R.id.nav_setting -> PrTabMenu.SETTING
                    else -> PrTabMenu.STATICS
                }

                switchPage(menuSelect)
                false
            }
        }

        /**
         * @param context DashBoardActivity
         * @param layout TabLayout
         * @param switchPage DashBoardActivity::switchPage()
         * @param tabClicked DashBoardActivity::onTabClicked()
         */
        fun tabLayout(
            context: Context,
            layout: TabLayout,
            switchPage:(PrTabMenu) -> Unit,
            tabClicked:(PrTabMenu) -> Unit
        ) {
            layout.run {
                for (idx in 0..4) {
                    val tabMenu = PrTabMenu.getTabByOrder(idx)
                    addTab(layout.newTab().setText(context.getString(tabMenu.menuName)), idx)
                }

                switchPage(PrTabMenu.STATICS)

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        tabClicked(PrTabMenu.getTabByOrder(tab.position))
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab) {
                        tabClicked(PrTabMenu.getTabByOrder(tab.position))
                    }
                })
            }
        }
    }
}