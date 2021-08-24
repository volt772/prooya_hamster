package com.apx5.apx5.navigator

import android.content.Context
import com.apx5.apx5.datum.ops.OpsTeamDetail

/**
 * PrNavigator
 */
interface PrNavigator {
    fun showDetailLists(context: Context, games: List<OpsTeamDetail>)
}