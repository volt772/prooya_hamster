package com.apx5.apx5.ui.recordall

import android.os.Bundle
import android.view.View
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.databinding.FragmentRecordAllBinding
import com.apx5.apx5.datum.DtAllGames
import com.apx5.apx5.datum.adapter.AdtPlayDelTarget
import com.apx5.apx5.datum.adapter.AdtPlayLists
import com.apx5.apx5.model.ResourceDelHistory
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utils.UiUtils
import com.apx5.apx5.utils.CommonUtils
import com.apx5.apx5.utils.equalsExt
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * RecordAllFragment
 */

class RecordAllFragment : BaseFragment<FragmentRecordAllBinding, RecordAllViewModel>(), RecordAllNavigator {
    private val recordAllViewModel: RecordAllViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_record_all
    }

    override fun getViewModel(): RecordAllViewModel {
        recordAllViewModel.setNavigator(this)
        return recordAllViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    private lateinit var recordAdapter: RecordAllAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscriber(UiUtils.currentYear)
    }

    /* 연도선택*/
    override fun selectYear(year: Int) {
        subscriber(year)
    }

    /* SpinKit 제거*/
    override fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /* UI 초기화*/
    private fun initView() {
        recordAdapter = RecordAllAdapter(requireContext(), this)
        binding().lvPlayLists.adapter = recordAdapter

        /* 시즌 변경*/
        binding().tvSearchYear.setOnClickListener {
            val seasonSelectDialog = YearSelectDialog.getInstance(this)
            seasonSelectDialog.show(childFragmentManager, "selectYear")
        }
    }

    /* 기록삭제*/
    override fun delHistoryItem(delPlay: AdtPlayDelTarget) {
        val email = PrefManager.getInstance(requireContext()).userEmail?: ""

        if (!email.equalsExt("")) {
            DialogActivity.dialogHistoryDelete(
                requireContext(),
                ResourceDelHistory(
                    pid = email,
                    rid = delPlay.id,
                    year = delPlay.season,
                    versus = delPlay.versus,
                    result = delPlay.result
                ),
                ::delHistory)
        }
    }

    /* 기록 삭제*/
    private fun delHistory(delHistory: ResourceDelHistory) {
        getViewModel().delHistory(delHistory)
    }

    /* 리스트 분기*/
    private fun isListExists(exists: Boolean) {
        binding().rlPlayLists.visibility = CommonUtils.setVisibility(exists)
        binding().rlEmptyList.visibility = CommonUtils.setVisibility(!exists)
    }

    /* 기록 리스트 생성*/
    override fun setHistory(plays: List<DtAllGames>, year: Int) {
        var isVisible = true

        recordAdapter.clearItems()
        if (plays.isEmpty()) {
            isVisible = false
        }

        isListExists(isVisible)

        for (play in plays) {
            val myTeamCode = PrefManager.getInstance(requireContext()).userTeam

            recordAdapter.addItem(
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

        recordAdapter.notifyDataSetChanged()

        binding().tvSearchYear.text = String.format(Locale.getDefault(), resources.getString(R.string.season_label), year)
    }

    /* Observers*/
    private fun subscriber(year: Int) {
        val email = PrefManager.getInstance(requireContext()).userEmail

        if (email != null) {
            getViewModel().getAllPlayLists(email, year)
        }
    }

    companion object {

        fun newInstance(): RecordAllFragment {
            val args = Bundle()
            val fragment = RecordAllFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

