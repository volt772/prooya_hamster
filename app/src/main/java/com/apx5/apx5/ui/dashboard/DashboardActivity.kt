package com.apx5.apx5.ui.dashboard

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseActivity
import com.apx5.apx5.databinding.ActivityDashboardBinding
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.days.DaysFragment
import com.apx5.apx5.ui.recordall.RecordAllFragment
import com.apx5.apx5.ui.recordteam.RecordTeamFragment
import com.apx5.apx5.ui.setting.SettingFragment
import com.apx5.apx5.ui.statics.StaticsFragment
import com.apx5.apx5.ui.team.TeamActivity
import com.apx5.apx5.ui.utils.MaterialTools
import com.apx5.apx5.utils.equalsExt
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * DashBoardActivity
 */

class DashBoardActivity : BaseActivity<ActivityDashboardBinding, DashBoardViewModel>() {
    private val dashBoardViewModel: DashBoardViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_dashboard
    }

    override fun getViewModel(): DashBoardViewModel {
        return dashBoardViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        seedToStart()
    }

    /* 시작분기*/
    private fun seedToStart() {
        val team = PrefManager.getInstance(this).userTeam

        if (team.equalsExt("")) {
            selectMyTeam()
        }

        initTabLayout()
    }

    /* 팀선택*/
    private fun selectMyTeam() {
        /* 팀 없는경우 팀선택 후 진행*/
        val intentTeam = TeamActivity.newIntent(this@DashBoardActivity)
        startActivity(intentTeam)
        finish()
    }

    /* 툴바설정*/
    private fun initToolbar() {
        val toolbar = getViewDataBinding().tbDashboard
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            setDisplayShowTitleEnabled(false)
        }

        MaterialTools.setSystemBarColor(this, R.color.p_navy_10)
    }

    /* 탭레이아웃 초기화*/
    private fun initTabLayout() {
        val tabLayout = getViewDataBinding().tlDashboard

        tabLayout?.run {
            addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_0), 0)
            addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_1), 1)
            addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_2), 2)
            addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_3), 3)
            addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_4), 4)

            getTabAt(0)?.icon?.setColorFilter(ContextCompat.getColor(application, R.color.red_85), PorterDuff.Mode.SRC_IN)
            getTabAt(1)?.icon?.setColorFilter(ContextCompat.getColor(application, R.color.grey_300), PorterDuff.Mode.SRC_IN)
            getTabAt(2)?.icon?.setColorFilter(ContextCompat.getColor(application, R.color.grey_300), PorterDuff.Mode.SRC_IN)
            getTabAt(3)?.icon?.setColorFilter(ContextCompat.getColor(application, R.color.grey_300), PorterDuff.Mode.SRC_IN)
            getTabAt(4)?.icon?.setColorFilter(ContextCompat.getColor(application, R.color.grey_300), PorterDuff.Mode.SRC_IN)

            switchPage(0)

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    tab.icon?.setColorFilter(ContextCompat.getColor(application, R.color.red_85), PorterDuff.Mode.SRC_IN)
                    onTabClicked(tab)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    tab.icon?.setColorFilter(ContextCompat.getColor(application, R.color.grey_300), PorterDuff.Mode.SRC_IN)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                    onTabClicked(tab)
                }
            })
        }
    }

    /* 탭클릭*/
    private fun onTabClicked(tab: TabLayout.Tab) {
        switchPage(tab.position)
    }

    /* 페이지 변경*/
    private fun switchPage(pos: Int) {
        val selectedFragment: Fragment

        when (pos) {
            0 -> selectedFragment = StaticsFragment.newInstance()
            1 -> selectedFragment = RecordTeamFragment.newInstance()
            2 -> selectedFragment = RecordAllFragment.newInstance()
            3 -> selectedFragment = DaysFragment.newInstance()
            4 -> selectedFragment = SettingFragment.newInstance()
            else -> selectedFragment = StaticsFragment.newInstance()
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, selectedFragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, DashBoardActivity::class.java)
        }
    }
}