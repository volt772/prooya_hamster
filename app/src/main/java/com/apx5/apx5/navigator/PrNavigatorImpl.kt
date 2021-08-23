package com.apx5.apx5.navigator

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import com.apx5.apx5.ProoyaClient.Companion.appContext
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.datum.ops.OpsTeamDetail
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.dialogs.DialogTeamDetail
import com.apx5.apx5.ui.recordteam.RecordTeamFragment
import com.apx5.apx5.ui.recordteam.RecordTeamViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * PrNavigatorImpl
 */
class PrNavigatorImpl @Inject constructor(@ApplicationContext context: Context) : PrNavigator {
//class PrNavigatorImpl @Inject constructor(private val activity: Fragment) : PrNavigator {
//class PrNavigatorImpl @Inject constructor(private val activity: RecordTeamFragment) : PrNavigator {
//class PrNavigatorImpl @Inject constructor(private val activity: FragmentActivity, private val vm: RecordTeamViewModel) : PrNavigator {
//    override fun getDetailLists(year: Int, versus: String) {
//        println("probe : getDetailLists : year : ${year}, versus : ${versus}, activity : ${activity}")

//        val email = PrefManager.getInstance(activity).userEmail?: ""
//        if (email.isNotBlank()) {
////            detailVersusTeam = versus
//            println("probe : PrNavigatorImpl : email : ${email}")
//            vm.fetchDetails(email, versus, year)
//        }
//    }

    override fun showDetailLists(context: Context, games: List<OpsTeamDetail>) {
        println("probe : showDetailLists : ${games.isNotEmpty()}")
        if (games.isNotEmpty()) {
            val versusTeam = games[0].playVs
            val detailDialog = DialogTeamDetail(games, versusTeam)
//            detailDialog.show(context.applicationContext.supportFragmentManager, "detailDialog")
//            detailDialog.show(supportFragmentManager, "detailDialog")
        } else {
            DialogActivity.dialogNoRecordDetail(context)
        }
    }
}