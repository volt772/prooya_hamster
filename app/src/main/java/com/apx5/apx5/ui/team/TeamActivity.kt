package com.apx5.apx5.ui.team

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseActivity
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.constants.PrTeamChangeMode
import com.apx5.apx5.databinding.ActivityTeamBinding
import com.apx5.apx5.datum.adapter.AdtTeamSelection
import com.apx5.apx5.network.operation.PrObserver
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utils.MaterialTools
import com.apx5.apx5.ui.utils.UiUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * TeamActivity
 */

@AndroidEntryPoint
class TeamActivity : BaseActivity<ActivityTeamBinding>() {

    private var teamSelectMode: PrTeamChangeMode?= null
    private lateinit var teamListAdapter: TeamListAdapter

    private val tvm: TeamViewModel by viewModels()
    override fun getLayoutId() = R.layout.activity_team
    override fun getBindingVariable() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(intent) {
            teamSelectMode = getSerializableExtra(PrConstants.Teams.TEAM_CHANGE_MODE) as PrTeamChangeMode?
        }

        initToolbar()
        initComponent()

        subscriber()
    }

    /* Toolbar*/
    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = resources.getString(R.string.team_select)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        MaterialTools.setSystemBarColor(this, R.color.p_main_first)
    }

    /* Components*/
    private fun initComponent() {
        val teamView = binding().rvTeam

        teamListAdapter = TeamListAdapter(getAppContext(), ::selectMyTeam)
        teamView.adapter = teamListAdapter

        /* 팀정보리스트 생성*/
        setTeamList(this)
    }

    /* 사용자 팀선택완료*/
    private fun finishSetMyTeam(code: String) {
        PrefManager.getInstance(this).setString(PrPrefKeys.MYTEAM, code)
        tvm.saveTeam(code)
    }

    /* 팀선택 최종 확인*/
    private fun selectMyTeam(team: AdtTeamSelection) {
        DialogActivity.dialogTeamSelect(this, team, ::finishSetMyTeam)
    }

    /* 팀리스트 생성*/
    private fun setTeamList(ctx: Context) {
        PrTeam.values().forEach { team ->
            if (team != PrTeam.OTHER) {
                val teamImage = UiUtils.getDrawableByName(this, team.emblem)
                teamListAdapter.addItem(
                    AdtTeamSelection(
                        teamImage = teamImage,
                        teamEmblem = ContextCompat.getDrawable(ctx, teamImage),
                        teamName = team.fullName,
                        teamCode = team.code,
                        teamColor = Color.parseColor(team.mainColor)
                    )

                )
            }
        }
    }

    /**
     * 신규선택 : DashBoardActivity
     * 기존변경 : 앱재시작
     */
    private fun switchPageBySelectType() {
        when (teamSelectMode) {
            PrTeamChangeMode.APPLY -> finish()
            PrTeamChangeMode.CHANGE -> restartApp()
            else -> restartApp()
        }
    }

    /* 계정삭제후, 앱재시작*/
    private fun vectoredRestart() {
        PrefManager.getInstance(this).setString(PrPrefKeys.MYTEAM, "")
        restartApp()
    }

    /* 앱재시작 (등록실패 or 팀변경)*/
    private fun restartApp() {
        val packageManager = application.packageManager
        val intent = packageManager.getLaunchIntentForPackage(application.packageName)
        val componentName = intent?.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        application.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) { android.R.id.home -> finish() }
        return super.onOptionsItemSelected(item)
    }

    private fun subscriber() {
        tvm.getTeamPostResult().observe(this, PrObserver {
            switchPageBySelectType()
        })
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, TeamActivity::class.java)
    }
}