package com.apx5.apx5.ui.dashboard

import android.content.Context
import com.apx5.apx5.constants.PrTabMenu
import com.google.android.material.tabs.TabLayout

/**
 * DashBoardTab
 * @desc 탭메뉴
 */

class DashBoardTab {
    companion object {
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