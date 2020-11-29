package com.apx5.apx5.ui.recordteam

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentRecordTeamBinding
import com.apx5.apx5.datum.DtTeamRecord
import com.apx5.apx5.datum.adapter.AdtTeamLists
import com.apx5.apx5.datum.ops.OpsTeamDetail
import com.apx5.apx5.datum.ops.OpsTeamSummary
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.dialogs.DialogSeasonChange
import com.apx5.apx5.ui.dialogs.DialogTeamDetail
import com.apx5.apx5.ui.utils.UiUtils
import com.apx5.apx5.utils.equalsExt
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * RecordTeamFragment
 */

class RecordTeamFragment :
    BaseFragment<FragmentRecordTeamBinding, RecordTeamViewModel>(),
    RecordTeamNavigator {

    private val recordTeamViewModel: RecordTeamViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_record_team
    }

    override fun getViewModel(): RecordTeamViewModel {
        recordTeamViewModel.setNavigator(this)
        return recordTeamViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    private var teamCode: String
    private lateinit var recordTeamAdapter: RecordTeamAdapter

    init {
        teamCode = ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamCode = PrefManager.getInstance(requireContext()).userTeam?: ""

        if (teamCode.equalsExt("")) {
            DialogActivity.dialogError(requireContext())
        }

        initView()
        subscriber(UiUtils.currentYear)
    }

    /* UI 초기화*/
    private fun initView() {
        /* 카드 컬러*/
        binding().clSeasonRate.setBackgroundColor(
                Color.parseColor(PrTeam.getTeamByCode(teamCode).mainColor)
        )

        /* 팀리스트*/
        recordTeamAdapter = RecordTeamAdapter(this)
        binding().lvTeamRecord.adapter = recordTeamAdapter

        /* 시즌변경 버튼*/
        binding().btnChangeSeason.setOnClickListener {
            val seasonSelectDialog = DialogSeasonChange(::selectSeasonYear)
            seasonSelectDialog.show(childFragmentManager, "selectSeason")
        }
    }

    /* SpinKit 제거*/
    override fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /* 시즌선택*/
    private fun selectSeasonYear(year: Int) {
        subscriber(year)
    }

    /* 상세정보 데이터*/
    override fun getDetailLists(year: Int, versus: String) {
        val email = PrefManager.getInstance(requireContext()).userEmail?: ""

        if (!email.equalsExt("")) {
            getViewModel().getDetails(email, versus, year)
        }
    }

    /* 상세정보 Dialog*/
    override fun showDetailLists(plays: List<OpsTeamDetail>, versus: String) {
        if (plays.isNotEmpty()) {
            val detailDialog = DialogTeamDetail(plays, versus)
            detailDialog.show(childFragmentManager, "detailDialog")
        } else {
            DialogActivity.dialogNoRecordDetail(requireContext())
        }
    }

    /* 팀 기록 리스트*/
    override fun setTeamRecord(teams: List<DtTeamRecord>) {
        recordTeamAdapter.clearItems()

        for (team in teams) {
            if (teamCode != team.team) {
                recordTeamAdapter.addItem(
                    AdtTeamLists(
                        year = team.year,
                        team = team.team,
                        win = team.win,
                        draw = team.draw,
                        lose = team.lose,
                        rate = team.rate,
                        teamEmblem = UiUtils.getDrawableByName(
                            requireContext(),
                            PrConstants.Teams.EMBLEM_PREFIX.plus(team.team)
                        )
                    )
                )
            }
        }

        recordTeamAdapter.notifyDataSetChanged()
    }

    /* 상단 헤더 요약*/
    override fun setHeaderSummary(summary: OpsTeamSummary) {
        binding().tvBoxTitle.text = String.format(Locale.getDefault(), resources.getString(R.string.season_label), summary.year)
        binding().tvSeasonStatic.text =
            String.format(
                Locale.getDefault(),
                resources.getString(R.string.w_d_l), summary.win, summary.draw, summary.lose
            )

        binding().tvTeamName.text = PrTeam.getTeamByCode(teamCode).fullName
        binding().ivTeamEmblem.setImageResource(
            UiUtils.getDrawableByName(
                requireContext(),
                PrConstants.Teams.EMBLEM_PREFIX.plus(teamCode)
            )
        )
    }

    /* Observers*/
    private fun subscriber(year: Int) {
        val email = PrefManager.getInstance(requireContext()).userEmail?: ""

        if (email.equalsExt("")) {
            DialogActivity.dialogError(requireContext())
        } else {
            getViewModel().getRecords(email, year)
        }
    }

    companion object {
        fun newInstance(): RecordTeamFragment {
            val args = Bundle()
            val fragment = RecordTeamFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
