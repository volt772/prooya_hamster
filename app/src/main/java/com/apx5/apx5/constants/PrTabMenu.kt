package com.apx5.apx5.constants

import androidx.fragment.app.Fragment
import com.apx5.apx5.R
import com.apx5.apx5.ui.days.DaysFragment
import com.apx5.apx5.ui.recordall.RecordAllFragment
import com.apx5.apx5.ui.recordteam.RecordTeamFragment
import com.apx5.apx5.ui.setting.SettingFragment
import com.apx5.apx5.ui.statics.StaticsFragment

/**
 * 탭아이콘
 * @return PrTabMenu
 */

enum class PrTabMenu(
    val iconName: String,
    val color: Int,
    val menuName: Int,
    val fragment: Fragment) {

    STATICS("ic_tab_0", R.color.red_85, R.string.menu0, StaticsFragment.newInstance()),
    RECORD_TEAM("ic_tab_1", R.color.grey_300, R.string.menu1, RecordTeamFragment.newInstance()),
    RECORD_ALL("ic_tab_2", R.color.grey_300, R.string.menu2, RecordAllFragment.newInstance()),
    DAYS("ic_tab_3", R.color.grey_300, R.string.menu3, DaysFragment.newInstance()),
    SETTING("ic_tab_4", R.color.grey_300, R.string.menu4, SettingFragment.newInstance()),
    OTHER("", 0, 0, StaticsFragment.newInstance());

    companion object {
        fun getTabByOrder(order: Int): PrTabMenu {
            return when (order) {
                0 -> STATICS
                1 -> RECORD_TEAM
                2 -> RECORD_ALL
                3 -> DAYS
                4 -> SETTING
                else -> OTHER
            }
        }
    }
}
