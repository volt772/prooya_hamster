package com.apx5.apx5.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseActivity2
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrTabMenu
import com.apx5.apx5.constants.PrTeamChangeMode
import com.apx5.apx5.databinding.ActivityDashboardBinding
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.team.TeamActivity
import com.apx5.apx5.ui.utils.MaterialTools
import com.apx5.apx5.utils.equalsExt

/**
 * DashBoardActivity
 */

class DashBoardActivity : BaseActivity2<ActivityDashboardBinding>() {

    override fun getLayoutId() = R.layout.activity_dashboard
    override fun getBindingVariable() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        seedToStart()
    }

    /**
     * 시작분기
     * @desc 선택 팀 없는경우, 팀을 먼저 선픽하고 온다.
     */
    private fun seedToStart() {
        val team = PrefManager.getInstance(this).userTeam
        if (team.equalsExt("")) {
            val intentTeam = TeamActivity.newIntent(this)
            intentTeam.putExtra(PrConstants.Teams.TEAM_CHANGE_MODE, PrTeamChangeMode.APPLY)
            startActivity(intentTeam)
            finish()
        }

        initBottomNav()
    }

    /**
     * 툴바생성
     */
    private fun initToolbar() {
        val toolbar = binding().tbDashboard
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            setDisplayShowTitleEnabled(false)
        }

        MaterialTools.setSystemBarColor(this, R.color.p_main_first)
    }

    /**
     * 하단 네비게이션
     */
    private fun initBottomNav() {
        switchPage(PrTabMenu.STATICS)

        DashBoardNav.bottomNavLayout(
            view = binding().navDashboard,
            switchPage = ::switchPage
        )
    }

    /**
     * 페이지 변경
     * @param tab PrTabMenu
     */
    private fun switchPage(tab: PrTabMenu) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.frame_layout, tab.fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu) = true

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, DashBoardActivity::class.java)
    }
}