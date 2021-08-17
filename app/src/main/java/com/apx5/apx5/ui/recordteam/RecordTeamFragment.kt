package com.apx5.apx5.ui.recordteam

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrStatus
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentRecordTeamBinding
import com.apx5.apx5.datum.DtTeamRecord
import com.apx5.apx5.datum.adapter.AdtTeamLists
import com.apx5.apx5.datum.ops.OpsTeamDetail
import com.apx5.apx5.datum.ops.OpsTeamRecords
import com.apx5.apx5.datum.ops.OpsTeamSummary
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.dialogs.DialogSeasonChange
import com.apx5.apx5.ui.dialogs.DialogTeamDetail
import com.apx5.apx5.ui.utils.UiUtils
import java.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * RecordTeamFragment
 */

class RecordTeamFragment :
    BaseFragment<FragmentRecordTeamBinding>(),
    RecordTeamNavigator {

    private var selectedYear: Int = 0

    private val rtvm: RecordTeamViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_record_team
    override fun getBindingVariable() = BR.viewModel

    private var teamCode: String
    private var detailVersusTeam: String
    private lateinit var recordTeamAdapter: RecordTeamAdapter

    init {
        teamCode = ""
        detailVersusTeam = ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamCode = PrefManager.getInstance(requireContext()).userTeam?: ""

        rtvm.setNavigator(this)

        if (teamCode.isBlank()) {
            DialogActivity.dialogError(requireContext())
        }

        initView()
        subscriber()

        val queryYear = if (selectedYear == 0) UiUtils.currentYear else selectedYear
        recordByYear(queryYear)
    }

    /* UI 초기화*/
    private fun initView() {
        /* 팀리스트*/
        recordTeamAdapter = RecordTeamAdapter(this)
        binding().lvTeamRecord.adapter = recordTeamAdapter

        /* 시즌변경 버튼*/
        binding().btnChangeSeason.setOnClickListener {
            val seasonSelectDialog = DialogSeasonChange(::selectSeasonYear, selectedYear)
            seasonSelectDialog.show(childFragmentManager, "selectSeason")
        }
    }

    /* SpinKit 제거*/
    private fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /* 시즌선택*/
    private fun selectSeasonYear(year: Int) {
        recordByYear(year)
        selectedYear = year
    }

    /* 상세정보 데이터*/
    override fun getDetailLists(year: Int, versus: String) {
        val email = PrefManager.getInstance(requireContext()).userEmail?: ""
        if (email.isNotBlank()) {
            detailVersusTeam = versus
            rtvm.fetchDetails(email, versus, year)
        }
    }

    /* 상세정보 Dialog*/
    override fun showDetailLists(plays: List<OpsTeamDetail>) {
        if (plays.isNotEmpty()) {
            val detailDialog = DialogTeamDetail(plays, detailVersusTeam)
            detailDialog.show(childFragmentManager, "detailDialog")
        } else {
            DialogActivity.dialogNoRecordDetail(requireContext())
        }
    }

    /* 팀 기록 리스트*/
    private fun setTeamRecord(teams: List<DtTeamRecord>) {
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
    private fun setHeaderSummary(summary: OpsTeamSummary) {
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

    private fun recordByYear(year: Int) {
        val email = PrefManager.getInstance(requireContext()).userEmail?: ""

        if (email.isBlank()) {
            DialogActivity.dialogError(requireContext())
        } else {
            rtvm.fetchRecords(email, year)
        }
    }

    private fun subscriber() {
        rtvm.getTeams().observe(viewLifecycleOwner, {
            when (it.status) {
                PrStatus.SUCCESS -> {
                    setTeamSummaryItems(it.data?.teams)
                    setHeaderSummaryItems(it.data?.summary)
                    cancelSpinKit()
                }
                PrStatus.LOADING,
                PrStatus.ERROR -> {}
            }
        })
    }

    private fun setTeamSummaryItems(teams: List<OpsTeamRecords>?) {
        val listTeam = ArrayList<DtTeamRecord>()

        teams?.let { _team ->
            _team.forEach {
                val teamEntity = DtTeamRecord(
                    year = it.year,
                    team = it.team,
                    win = it.win,
                    lose = it.lose,
                    draw = it.draw,
                    rate = it.rate
                )

                listTeam.add(teamEntity)
            }

            setTeamRecord(listTeam)
        }
    }

    /* 팀헤더 요약정보*/
    private fun setHeaderSummaryItems(summary: OpsTeamSummary?) {
        summary?.let { setHeaderSummary(it) }
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