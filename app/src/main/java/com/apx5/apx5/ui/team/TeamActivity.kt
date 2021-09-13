package com.apx5.apx5.ui.team

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseActivity
import com.apx5.apx5.constants.*
import com.apx5.apx5.databinding.ActivityTeamBinding
import com.apx5.apx5.datum.adapter.AdtTeamSelection
import com.apx5.apx5.ext.DividerItemDecorator
import com.apx5.apx5.ext.drawableRes
import com.apx5.apx5.ext.itemDecorationExt
import com.apx5.apx5.ext.setSystemBarColor
import com.apx5.apx5.operation.PrObserver
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.ui.adapter.PrCentralAdapter
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utilities.PrUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * TeamActivity
 */

@AndroidEntryPoint
class TeamActivity : BaseActivity<ActivityTeamBinding>() {

    @Inject
    lateinit var prPreference: PrPreference

    @Inject
    lateinit var prUtils: PrUtils

    private val tvm: TeamViewModel by viewModels()

    override fun getLayoutId() = R.layout.activity_team
    override fun getBindingVariable() = BR.viewModel

    private var teamSelectMode: PrTeamChangeMode?= null
    private lateinit var prCentralAdapter: PrCentralAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(intent) {
            teamSelectMode = getSerializableExtra(PrConstants.Teams.TEAM_CHANGE_MODE) as PrTeamChangeMode?
        }

        initToolbar()
        initComponent()

        subscriber()
    }

    /**
     * initToolbar
     */
    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = resources.getString(R.string.team_select)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        setSystemBarColor(this, R.color.p_main_first)
    }

    /**
     * initComponent
     */
    private fun initComponent() {
        val teams = getTeamList()
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        prCentralAdapter = PrCentralAdapter(
            context = this,
            viewType = PrAdapterViewType.TEAM_SELECTION,
            prUtils = prUtils,
            selectTeam = ::selectMyTeam
        )

        binding().apply {
            rvTeam.apply {
                layoutManager = linearLayoutManager
                adapter = prCentralAdapter
                itemDecorationExt(
                    listOf(
                        DividerItemDecorator(
                            drawableRes(R.drawable.divider),
                            prUtils.dpToPx(16)
                        ),
                    )
                )
            }
        }

        prCentralAdapter.apply {
            addTeamSelection(teams)
            notifyDataSetChanged()
        }
    }

    /**
     * getTeamList
     * @desc 팀리스트 생성
     */
    private fun getTeamList() =  mutableListOf<AdtTeamSelection>().also { _list ->
        PrTeam.values().forEach { team ->
            if (team != PrTeam.OTHER) {
                val teamImage = prUtils.getDrawableByName(this, team.emblem)
                _list.add(
                    AdtTeamSelection(
                        teamImage = teamImage,
                        teamEmblem = ContextCompat.getDrawable(this, teamImage),
                        teamName = team.fullName,
                        teamCode = team.code,
                        teamColor = Color.parseColor(team.mainColor)
                    )
                )
            }
        }
    }

    /**
     * finishSetMyTeam
     * @desc 사용자 팀선택완료
     */
    private fun finishSetMyTeam(code: String) {
        prPreference.setString(PrPrefKeys.MY_TEAM, code)
        tvm.saveTeam(code)
    }

    /**
     * selectMyTeam
     * @desc 팀선택 최종 확인
     */
    private fun selectMyTeam(team: AdtTeamSelection) {
        teamSelectMode?.let { _mode ->
            val msg = if (_mode == PrTeamChangeMode.APPLY) {
                String.format(resources.getString(R.string.team_select_dialog_apply), team.teamName)
            } else {
                resources.getString(R.string.team_select_dialog_change)
            }

            DialogActivity.dialogTeamSelect(this, team, msg, ::finishSetMyTeam)
        }
    }

    /**
     * switchPageBySelectType
     * @desc 신규선택 : DashBoardActivity
     * @desc 기존변경 : 앱재시작
     */
    private fun switchPageBySelectType() {
        when (teamSelectMode) {
            PrTeamChangeMode.APPLY -> finish()
            PrTeamChangeMode.CHANGE -> restartApp()
            else -> restartApp()
        }
    }

    /**
     * restartApp
     * @desc 앱재시작 (등록실패 or 팀변경)
     */
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