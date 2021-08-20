package com.apx5.apx5.navigator

import android.app.Activity
import android.content.Context
import com.apx5.apx5.datum.ops.OpsTeamDetail

/**
 * PrNavigator
 */
interface PrNavigator {
//    fun getDetailLists(year: Int, versus: String)
    fun showDetailLists(context: Context, games: List<OpsTeamDetail>)
}