package com.apx5.apx5.ui.statics

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.databinding.FragmentStaticsBinding
import com.apx5.apx5.datum.DtStatics
import com.apx5.apx5.datum.adapter.AdtTeamWinningRate
import com.apx5.apx5.datum.ops.OpsUser
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.adapter.TeamWinningRateAdapter
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.utils.equalsExt
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * StaticsFragment
 */

class StaticsFragment :
    BaseFragment<FragmentStaticsBinding, StaticsViewModel>(),
    StaticsNavigator {

    private val svm: StaticsViewModel by viewModel()

    private var userEmail: String = ""
    private var teamCode: String = ""

    private lateinit var teamWinningRateAdapter: TeamWinningRateAdapter

    override fun getLayoutId() = R.layout.fragment_statics
    override fun getBindingVariable() = BR.viewModel
    override fun getViewModel(): StaticsViewModel {
        svm.setNavigator(this)
        return svm
    }

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
    override fun saveUserInfo(user: OpsUser) {
        user.run {
            PrefManager.getInstance(requireActivity()).setString(PrPrefKeys.MYTEAM, team)
            PrefManager.getInstance(requireActivity()).setInt(PrPrefKeys.MY_ID, userId)
        }
    }

    /* SpinKit 제거*/
    override fun cancelSpinKit() {
        binding().clLoading.visibility = View.GONE
    }

    /**
     * 통계 수치값 지정
     */
    override fun setDatumChart(statics: DtStatics) {
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
    override fun setTeamWinningRate(teams: List<AdtTeamWinningRate>) {
        teamWinningRateAdapter.clearItems()
        teams.forEach { _team ->
            if (_team.team.code != teamCode) {
                teamWinningRateAdapter.addItem(_team)
            }
        }
    }

    private fun subscriber() {
        if (!userEmail.equalsExt("")) {
            getViewModel().getStatics(userEmail)
        } else {
            DialogActivity.dialogError(requireContext())
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