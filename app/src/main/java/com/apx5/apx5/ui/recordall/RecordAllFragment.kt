package com.apx5.apx5.ui.recordall

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.databinding.FragmentRecordAllBinding
import com.apx5.apx5.db.entity.PrPlayEntity
import com.apx5.apx5.model.ResourceDelHistory
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utils.UiUtils
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

    private lateinit var searchYear: TextView
    private lateinit var playLists: RelativeLayout
    private lateinit var emptyView: RelativeLayout

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
        getViewDataBinding().skLoading.visibility = View.GONE
    }

    /* UI 초기화*/
    private fun initView() {
        val playListView = getViewDataBinding().lvPlayLists
        searchYear = getViewDataBinding().tvSearchYear
        playLists = getViewDataBinding().rlPlayLists
        emptyView = getViewDataBinding().rlEmptyList

        recordAdapter = RecordAllAdapter(requireContext(), this)
        playListView.adapter = recordAdapter

        /* 시즌 변경*/
        searchYear.setOnClickListener {
            val seasonSelectDialog = YearSelectDialog.getInstance(this)
            seasonSelectDialog.show(childFragmentManager, "selectYear")
        }
    }

    /* 기록삭제*/
    override fun delHistoryItem(playId: String, playSeason: String, playVersus: String, playResult: String) {
        val email = PrefManager.getInstance(requireContext()).userEmail?: ""

        if (!email.equalsExt("")) {
            DialogActivity.dialogHistoryDelete(
                requireContext(),
                ResourceDelHistory(email, playId, playSeason, playVersus, playResult),
                ::delHistory)
        }
    }

    /* 기록 삭제*/
    private fun delHistory(delHistory: ResourceDelHistory) {
        getViewModel().delHistory(delHistory)
    }

    /* 리스트 분기*/
    private fun isListExists(exists: Boolean) {
        if (exists) {
            playLists.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        } else {
            playLists.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        }
    }

    /* 기록 리스트 생성*/
    override fun setHistory(plays: List<PrPlayEntity>, year: Int) {
        var isVisible = true

        recordAdapter.clearItems()
        if (plays.isEmpty()) {
            isVisible = false
        }

        isListExists(isVisible)

        for (play in plays) {
            val myTeamCode = PrefManager.getInstance(requireContext()).userTeam

            recordAdapter.addItem(
                    play.playId,
                    play.playSeason,
                    play.playVersus,
                    play.playResult,
                    play.playDate,
                    play.playPtGet,
                    play.playPtLost,
                    resources.getIdentifier(PrConstants.Teams.EMBLEM_PREFIX.plus(myTeamCode), "drawable", requireActivity().packageName),
                    resources.getIdentifier(PrConstants.Teams.EMBLEM_PREFIX.plus(play.playVersus), "drawable", requireActivity().packageName)
            )
        }

        recordAdapter.notifyDataSetChanged()

        searchYear.text = String.format(Locale.getDefault(), resources.getString(R.string.season_label), year)
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

