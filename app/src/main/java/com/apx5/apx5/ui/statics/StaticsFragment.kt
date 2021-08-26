package com.apx5.apx5.ui.statics

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentStaticsBinding
import com.apx5.apx5.datum.DtStatics
import com.apx5.apx5.datum.adapter.AdtTeamWinningRate
import com.apx5.apx5.datum.ops.OpsAllStatics
import com.apx5.apx5.datum.ops.OpsTeamWinningRate
import com.apx5.apx5.datum.ops.OpsUser
import com.apx5.apx5.network.operation.PrObserver
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.ui.adapter.TeamWinningRateAdapter
import com.apx5.apx5.ui.dialogs.DialogActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * StaticsFragment
 */

@AndroidEntryPoint
class StaticsFragment : BaseFragment<FragmentStaticsBinding>() {

    @Inject
    lateinit var prPreference: PrPreference

    @Inject
    lateinit var teamWinningRateAdapter: TeamWinningRateAdapter

    private val svm: StaticsViewModel by viewModels()

    private var userEmail: String = ""
    private var teamCode: String = ""

    override fun getLayoutId() = R.layout.fragment_statics
    override fun getBindingVariable() = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userEmail = prPreference.userEmail?: ""
        teamCode = prPreference.userTeam?: ""

        initView()
        subscriber()
    }

    /* UI 초기화*/
    private fun initView() {
        /* Adapter*/
        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        teamWinningRateAdapter = TeamWinningRateAdapter()

        binding().rvTeamPerList.apply {
            layoutManager = linearLayoutManager
            adapter = teamWinningRateAdapter
        }
    }

    /* 사용자 정보 저장*/
    private fun saveUserInfo(user: OpsUser) {
        user.run {
            prPreference.run {
                setString(PrPrefKeys.MY_TEAM, team)
                setInt(PrPrefKeys.MY_ID, userId)
            }
        }
    }

    /* SpinKit 제거*/
    private fun cancelSpinKit() {
        binding().clLoading.visibility = View.GONE
    }

    /**
     * 통계 수치값 지정
     */
    private fun setDatumChart(statics: DtStatics) {
        binding().apply {
            pcRecord.setProgress(statics.rateAll.toFloat(), true)
            /* 통산전적*/
            tvLeftRecord.text = String.format(
                resources.getString(R.string.w_d_l),
                    statics.countAllWin, statics.countAllDraw, statics.countAllLose
            )
            /* 통산직관횟수*/
            tvRightRecord.text = String.format(
                resources.getString(R.string.seeing_count_season),
                    statics.countAll
            )
        }
    }

    /**
     * 팀통산승률 그래프 처리
     */
    private fun setTeamWinningRate(teams: List<AdtTeamWinningRate>) {
        teamWinningRateAdapter.clearItems()
        teams.forEach { _team ->
            if (_team.team.code != teamCode) {
                teamWinningRateAdapter.addItem(_team)
            }
        }
    }

    private fun subscriber() {
        /* 통계자료 Fetch*/
        if (userEmail.isNotBlank()) {
            svm.fetchStatics(userEmail)
        } else {
            DialogActivity.dialogError(requireContext())
        }

        /* 통계자료 Display*/
        svm.getStatics().observe(viewLifecycleOwner, PrObserver {
            cancelSpinKit()
            setTeamCode(it.user)
            setStaticItem(it.allStatics)
            setTeamAllPercentageItem(it.teamWinningRate)
        })
    }

    /* 팀코드 저장*/
    private fun setTeamCode(user: OpsUser?) {
        user?.let { _user -> saveUserInfo(_user) }
    }

    /* 통계수치지정*/
    private fun setStaticItem(statics: OpsAllStatics?) {
        statics?.let { _statics ->
            setDatumChart(
                DtStatics(
                    countAll = _statics.count,
                    countAllDraw = _statics.draw,
                    countAllLose = _statics.lose,
                    countAllWin = _statics.win,
                    rateAll = _statics.rate
                )
            )
        }
    }

    private fun setTeamAllPercentageItem(teamData: OpsTeamWinningRate?) {
        val teams = mutableListOf<AdtTeamWinningRate>()
        teamData?.let { _teamData ->
            val list = listOf(
                AdtTeamWinningRate(PrTeam.DSB, _teamData.dsb),
                AdtTeamWinningRate(PrTeam.HHE, _teamData.hhe),
                AdtTeamWinningRate(PrTeam.KAT, _teamData.kat),
                AdtTeamWinningRate(PrTeam.KTW, _teamData.ktw),
                AdtTeamWinningRate(PrTeam.LGT, _teamData.lgt),
                AdtTeamWinningRate(PrTeam.LTG, _teamData.ltg),
                AdtTeamWinningRate(PrTeam.NCD, _teamData.ncd),
                AdtTeamWinningRate(PrTeam.KWH, _teamData.kwh),
                AdtTeamWinningRate(PrTeam.SKW, _teamData.skw),
                AdtTeamWinningRate(PrTeam.SSL, _teamData.ssl)
            )

            teams.addAll(list)

            val sorted = teams.sortedWith(compareByDescending { it.winningRate })
            setTeamWinningRate(sorted)
        }
    }

    companion object {
        fun newInstance() = StaticsFragment().apply {  }
    }
}