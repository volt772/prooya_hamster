package com.apx5.apx5.ui.statics

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment2
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.constants.PrStatus
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentStaticsBinding
import com.apx5.apx5.datum.DtStatics
import com.apx5.apx5.datum.adapter.AdtTeamWinningRate
import com.apx5.apx5.datum.ops.OpsAllStatics
import com.apx5.apx5.datum.ops.OpsTeamWinningRate
import com.apx5.apx5.datum.ops.OpsUser
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.adapter.TeamWinningRateAdapter
import com.apx5.apx5.ui.dialogs.DialogActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * StaticsFragment
 */

class StaticsFragment :
    BaseFragment2<FragmentStaticsBinding>() {

    private val svm: StaticsViewModel by viewModel()

    private var userEmail: String = ""
    private var teamCode: String = ""

    private lateinit var teamWinningRateAdapter: TeamWinningRateAdapter

    override fun getLayoutId() = R.layout.fragment_statics
    override fun getBindingVariable() = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userEmail = PrefManager.getInstance(requireContext()).userEmail?: ""
        teamCode = PrefManager.getInstance(requireContext()).userTeam ?: ""

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
    fun saveUserInfo(user: OpsUser) {
        user.run {
            PrefManager.getInstance(requireActivity()).setString(PrPrefKeys.MYTEAM, team)
            PrefManager.getInstance(requireActivity()).setInt(PrPrefKeys.MY_ID, userId)
        }
    }

    /* SpinKit 제거*/
    fun cancelSpinKit() {
        binding().clLoading.visibility = View.GONE
    }

    /**
     * 통계 수치값 지정
     */
    fun setDatumChart(statics: DtStatics) {
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
    fun setTeamWinningRate(teams: List<AdtTeamWinningRate>) {
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
            svm.getStatics(userEmail)
        } else {
            DialogActivity.dialogError(requireContext())
        }

        /* 통계자료 Display*/
        svm.getStatics().observe(viewLifecycleOwner, {
            when (it.status) {
                PrStatus.SUCCESS -> {
                    cancelSpinKit()
                    setTeamCode(it.data?.user)
                    setStaticItem(it.data?.allStatics)
                    setTeamAllPercentageItem(it.data?.teamWinningRate)
                }
                PrStatus.LOADING,
                PrStatus.ERROR -> {}
            }
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
                AdtTeamWinningRate(PrTeam.NXH, _teamData.nxh),
                AdtTeamWinningRate(PrTeam.SKW, _teamData.skw),
                AdtTeamWinningRate(PrTeam.SSL, _teamData.ssl)
            )

            teams.addAll(list)

            val sorted = teams.sortedWith(compareByDescending { it.winningRate })
            setTeamWinningRate(sorted)
        }
    }

    companion object {
        fun newInstance(): StaticsFragment {
            val args = Bundle()
            val fragment = StaticsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}