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
    val color: Int,
    val fragment: Fragment
) {
    /* 통계*/
    STATICS(
        R.color.red_85,
        StaticsFragment.newInstance()
    ),
    /* 팀기록*/
    RECORD_TEAM(
        R.color.grey_300,
        RecordTeamFragment.newInstance()
    ),
    /* 전체기록*/
    RECORD_ALL(
        R.color.grey_300,
        RecordAllFragment.newInstance()
    ),
    /* 일정*/
    DAYS(
        R.color.grey_300,
        DaysFragment.newInstance()
    ),
    /* 설정*/
    SETTING(
        R.color.grey_300,
        SettingFragment.newInstance()
    ),
    /* 기타*/
    OTHER(
        0,
        StaticsFragment.newInstance()
    );
}
