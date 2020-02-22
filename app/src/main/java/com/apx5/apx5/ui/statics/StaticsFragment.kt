package com.apx5.apx5.ui.statics

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.databinding.FragmentStaticsBinding
import com.apx5.apx5.datum.adapter.AdtPlayLists
import com.apx5.apx5.db.entity.PrPlayEntity
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utils.UiUtils
import com.apx5.apx5.utils.CommonUtils
import com.apx5.apx5.utils.equalsExt
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * StaticsFragment
 */

class StaticsFragment : BaseFragment<FragmentStaticsBinding, StaticsViewModel>(), StaticsNavigator {

    private val staticsViewModel: StaticsViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_statics
    }

    override fun getViewModel(): StaticsViewModel {
        staticsViewModel.setNavigator(this)
        return staticsViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    private lateinit var playListView: RelativeLayout
    private lateinit var emptyView: RelativeLayout

    private lateinit var recentPlayAdapter: RecentPlayAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscriber()
    }

    /* UI 초기화*/
    private fun initView() {
        binding()
        playListView = binding().rytPlayList
        emptyView = binding().rytEmptyList

        /* 최근기록 리스트*/
        val recentPlayList = binding().lvPlayLists
        recentPlayAdapter = RecentPlayAdapter()
        recentPlayList.adapter = recentPlayAdapter
    }

    /* 최근 5경기 리스트 생성*/
    private fun setRecentPlayLists(plays: List<PrPlayEntity>) {
        recentPlayAdapter.clearItems()
        val myTeamCode = PrefManager.getInstance(requireContext()).userTeam

        for (play in plays) {
            recentPlayAdapter.addItem(
                AdtPlayLists(
                    playVersus = play.playVersus,
                    playId = play.playId,
                    playSeason = play.playSeason,
                    playResult = play.playResult,
                    playDate = play.playDate,
                    scoreMy = play.playPtGet,
                    scoreVs = play.playPtLost,
                    emblemMy = UiUtils.getDrawableByName(
                        requireContext(),
                        PrConstants.Teams.EMBLEM_PREFIX.plus(myTeamCode)),
                    emblemVs = UiUtils.getDrawableByName(
                        requireContext(),
                        PrConstants.Teams.EMBLEM_PREFIX.plus(play.playVersus))
                )
            )
        }

        recentPlayAdapter.notifyDataSetChanged()
    }

    /* SpinKit 제거*/
    override fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /* 최근경기 리스트*/
    override fun showRecentPlayList(plays: List<PrPlayEntity>) {
        if (plays.isNotEmpty()) {
            setRecentPlayLists(plays)
        }

        playListView.visibility = CommonUtils.setVisibility(plays.isNotEmpty())
        emptyView.visibility = CommonUtils.setVisibility(plays.isEmpty())
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