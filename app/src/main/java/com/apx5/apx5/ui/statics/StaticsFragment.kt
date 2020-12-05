package com.apx5.apx5.ui.statics

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.*
import com.apx5.apx5.databinding.FragmentStaticsBinding
import com.apx5.apx5.datum.DtPlays
import com.apx5.apx5.datum.adapter.AdtGames
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.adapter.PlayItemsAdapter
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.utils.CommonUtils
import com.apx5.apx5.utils.equalsExt
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * StaticsFragment
 */

class StaticsFragment :
    BaseFragment<FragmentStaticsBinding, StaticsViewModel>(),
    StaticsNavigator {

    private val staticsViewModel: StaticsViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_statics
    override fun getBindingVariable() = BR.viewModel
    override fun getViewModel(): StaticsViewModel {
        staticsViewModel.setNavigator(this)
        return staticsViewModel
    }

    private lateinit var playItemsAdapter: PlayItemsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscriber()
    }

    /* UI 초기화*/
    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        playItemsAdapter = PlayItemsAdapter(requireContext(), PrAdapterViewType.RECENT)

        binding().rvRecentList.apply {
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            layoutManager = linearLayoutManager
            adapter = playItemsAdapter
        }
    }

    /* 최근 5경기 리스트 생성*/
    private fun setRecentPlayLists(plays: List<DtPlays>) {
        for (play in plays) {
            playItemsAdapter.addItem(
                AdtGames(
                    awayScore = play.awayScore,
                    awayTeam = play.awayTeam,
                    awayEmblem = PrTeam.getTeamByCode(play.awayTeam),
                    homeScore = play.homeScore,
                    homeTeam = play.homeTeam,
                    homeEmblem = PrTeam.getTeamByCode(play.homeTeam),
                    playDate = "${play.playDate}",
                    playId = play.playId,
                    playResult = PrResultCode.getResultByDisplayCode(play.playResult),
                    playSeason = play.playSeason,
                    playVersus = play.playVs,
                    stadium = PrStadium.getStadiumByCode(play.stadium).displayName
                )
            )
        }

        playItemsAdapter.notifyDataSetChanged()
    }

    /* 팀코드 엎어치기*/
    override fun saveMyTeamCode(teamCode: String) {
        if (!teamCode.equalsExt("")) {
            PrefManager.getInstance(requireActivity()).setString(PrPrefKeys.MYTEAM, teamCode)
        }
    }

    /* SpinKit 제거*/
    override fun cancelSpinKit() {
        binding().clLoading.visibility = View.GONE
    }

    /* 최근경기 리스트*/
    override fun showRecentPlayList(playList: List<DtPlays>) {
        if (playList.isNotEmpty()) {
            setRecentPlayLists(playList)
        }

        binding().rvRecentList.visibility = CommonUtils.setVisibility(playList.isNotEmpty())
        binding().clEmptyList.visibility = CommonUtils.setVisibility(playList.isEmpty())
    }

    private fun subscriber() {
        val email = PrefManager.getInstance(requireContext()).userEmail?: ""
        if (!email.equalsExt("")) {
            getViewModel().getStatics(email)
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