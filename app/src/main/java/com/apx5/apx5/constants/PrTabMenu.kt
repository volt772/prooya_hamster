package com.apx5.apx5.constants

import com.apx5.apx5.R

/**
 * 탭아이콘
 * @return PrTabMenu
 */

enum class PrTabMenu(
    val order: Int,
    val displayName: String,
    val iconName: String,
    val color: Int) {

    STATICS(0, "통계", "ic_tab_0", R.color.red_85),
    RECORD_TEAM(1, "팀별기록", "ic_tab_1", R.color.grey_300),
    RECORD_ALL(2, "전체기록", "ic_tab_2", R.color.grey_300),
    DAYS(3, "일별기록", "ic_tab_3", R.color.grey_300),
    SETTING(4, "설정", "ic_tab_4", R.color.grey_300),
    OTHER(999, "", "", 0);

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
