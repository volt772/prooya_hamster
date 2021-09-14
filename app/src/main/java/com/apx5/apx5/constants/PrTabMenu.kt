package com.apx5.apx5.constants

import androidx.fragment.app.Fragment
import com.apx5.apx5.R
import com.apx5.apx5.ui.histories.HistoriesFragment
import com.apx5.apx5.ui.scheduled.ScheduledFragment
import com.apx5.apx5.ui.seasons.SeasonsFragment
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
    /**
     * 통계
     */
    STATICS(
        R.color.red_85,
        StaticsFragment.newInstance()
    ),

    /**
     * 시즌기록
     */
   SEASON(
        R.color.grey_300,
        SeasonsFragment.newInstance()
    ),

   /**
   * 전체기록
   */
   RECORD_ALL(
       R.color.grey_300,
       HistoriesFragment.newInstance()
   ),

   /**
    * 일정
    */
   DAYS(
       R.color.grey_300,
       ScheduledFragment.newInstance()
   ),

   /**
    * 설정
    */
   SETTING(
       R.color.grey_300,
       SettingFragment.newInstance()
   ),

    /**
     * 기타
     */
   OTHER(
       0,
       StaticsFragment.newInstance()
   );
}