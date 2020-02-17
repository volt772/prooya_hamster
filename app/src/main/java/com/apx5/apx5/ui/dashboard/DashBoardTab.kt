package com.apx5.apx5.ui.dashboard

import android.content.Context
import android.graphics.PorterDuff
import androidx.core.content.ContextCompat
import com.apx5.apx5.R
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
            switchPage:(Int) -> Unit,
            tabClicked:(TabLayout.Tab) -> Unit
        ) {
            val appContext = context.applicationContext
            layout.run {
                for (idx in 0..4) {
                    val tabMenu = PrTabMenu.getTabByOrder(idx)

                    addTab(layout.newTab().setIcon(
                        resources.getIdentifier(tabMenu.iconName, "drawable", context.packageName)),
                        idx
                    )

                    getTabAt(idx)?.icon?.setColorFilter(
                        ContextCompat.getColor(appContext, tabMenu.color),
                        PorterDuff.Mode.SRC_IN
                    )
                }

                switchPage(0)

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        tab.icon?.setColorFilter(
                            ContextCompat.getColor(appContext, R.color.red_85),
                            PorterDuff.Mode.SRC_IN
                        )
                        tabClicked(tab)
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {
                        tab.icon?.setColorFilter(
                            ContextCompat.getColor(appContext, R.color.grey_300),
                            PorterDuff.Mode.SRC_IN
                        )
                    }

                    override fun onTabReselected(tab: TabLayout.Tab) { tabClicked(tab) }
                })
            }
        }
    }
}