package com.apx5.apx5.constants

import com.apx5.apx5.R

/**
 * 탭아이콘
 * @return PrTabMenu
 */

enum class PrTabMenu(
    val iconName: String,
    val color: Int) {

    STATICS("ic_tab_0", R.color.red_85),
    RECORD_TEAM("ic_tab_1", R.color.grey_300),
    RECORD_ALL("ic_tab_2", R.color.grey_300),
    DAYS("ic_tab_3", R.color.grey_300),
    SETTING("ic_tab_4", R.color.grey_300),
    OTHER("", 0);

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
