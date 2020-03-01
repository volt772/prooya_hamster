package com.apx5.apx5.ui.team

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseActivity
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.databinding.ActivityTeamBinding
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dashboard.DashBoardActivity
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utils.MaterialTools
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * TeamActivity
 */

class TeamActivity :
    BaseActivity<ActivityTeamBinding, TeamViewModel>(),
    TeamNavigator {

    private lateinit var teamListAdapter: TeamListAdapter

    private val teamViewModel: TeamViewModel by viewModel()
    override fun getLayoutId() = R.layout.activity_team
    override fun getBindingVariable() = BR.viewModel
    override fun getViewModel(): TeamViewModel {
        teamViewModel.setNavigator(this)
        return teamViewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initComponent()
    }

    /* Toolbar*/
    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = resources.getString(R.string.team_select)
            setDisplayHomeAsUpEnabled(false)
        }

        MaterialTools.setSystemBarColor(this, R.color.p_navy_10)
    }

    /* Components*/
    private fun initComponent() {
        val teamView = binding().rvTeam

        teamListAdapter = TeamListAdapter(getAppContext(), this)
        teamView.adapter = teamListAdapter

        /* 팀정보리스트 생성*/
        setTeamList(this)
    }

    /* 사용자 팀선택완료*/
    private fun finishSetMyTeam(code: String) {
        PrefManager.getInstance(this).setString(PrPrefKeys.MYTEAM, code)
        getViewModel().saveTeam(code)
    }

    /* 팀선택 최종 확인*/
    override fun selectMyTeam(team: TeamList) {
        DialogActivity.dialogTeamSelect(this, team, ::finishSetMyTeam)
    }

    /* 팀리스트 생성*/
    private fun setTeamList(ctx: Context) {
        val items = ArrayList<TeamList>()
        val teamEmblemArr = ctx.resources.obtainTypedArray(R.array.team_images)
        val teamNameArr = ctx.resources.getStringArray(R.array.team_name)
        val teamCodeArr = ctx.resources.getStringArray(R.array.team_code)
        val teamColorArr = ctx.resources.obtainTypedArray(R.array.team_color)

        for (i in 0 until teamEmblemArr.length()) {
            val obj = TeamList()
            obj.teamImage = teamEmblemArr.getResourceId(i, -1)
            obj.teamName = teamNameArr[i]
            obj.teamCode = teamCodeArr[i]
            obj.teamEmblem = ContextCompat.getDrawable(ctx, obj.teamImage)
            obj.teamColor = ContextCompat.getColor(ctx, teamColorArr.getResourceId(i, -1))
            items.add(obj)
        }
        items.shuffle()

        teamListAdapter.addItem(items)
    }

    /* DashBoard*/
    override fun switchToDashBoard() {
        val intentStatics = DashBoardActivity.newIntent(this@TeamActivity)
        startActivity(intentStatics)
        finish()
    }

    /* 계정삭제후, 앱재시작*/
    override fun vectoredRestart() {
        PrefManager.getInstance(this).setString(PrPrefKeys.MYTEAM, "")

        val packageManager = application.packageManager
        val intent = packageManager.getLaunchIntentForPackage(application.packageName)
        val componentName = intent?.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        application.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, TeamActivity::class.java)
    }
}