package com.apx5.apx5.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseActivity
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrTabMenu
import com.apx5.apx5.constants.PrTeamChangeMode
import com.apx5.apx5.databinding.ActivityDashboardBinding
import com.apx5.apx5.ext.setSystemBarColor
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.ui.team.TeamActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.system.exitProcess

/**
 * DashBoardActivity
 */

@AndroidEntryPoint
class DashBoardActivity : BaseActivity<ActivityDashboardBinding>() {

    @Inject
    lateinit var prPreference: PrPreference

    override fun getLayoutId() = R.layout.activity_dashboard
    override fun getBindingVariable() = BR.viewModel

    private var backPressedTime: Long = 0
    lateinit var backToast: Toast

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
        val team = prPreference.userTeam
        if (team.isNullOrBlank()) {
            val intentTeam = TeamActivity.newIntent(this)
            intentTeam.putExtra(PrConstants.Teams.TEAM_CHANGE_MODE, PrTeamChangeMode.APPLY)
            startActivity(intentTeam)
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

        setSystemBarColor(this, R.color.p_main_first)
    }

    /**
     * 하단 네비게이션
     */
    private fun initBottomNav() {
        switchPage(PrTabMenu.STATICS)

        DashBoardBotNavigator.bottomNavLayout(
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
        backToast = Toast.makeText(this, resources.getString(R.string.back_exit), Toast.LENGTH_LONG)
        if (backPressedTime + intervalTime > System.currentTimeMillis()) {
            backToast.cancel()
            super.onBackPressed()
            exitApp()
            return
        } else {
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    /**
     * 앱종료
     */
    private fun exitApp() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        startActivity(intent)
        finish()
        exitProcess(0)
    }

    companion object {
        const val intervalTime = 2000

        fun newIntent(context: Context) = Intent(context, DashBoardActivity::class.java)
    }
}