package com.apx5.apx5.ui.seasons

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrAdapterViewType
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrDialogYearSelectType
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentSeasonBinding
import com.apx5.apx5.datum.DtTeamRecord
import com.apx5.apx5.datum.adapter.AdtTeamLists
import com.apx5.domain.ops.OpsTeamDetail
import com.apx5.domain.ops.OpsTeamRecords
import com.apx5.domain.ops.OpsTeamSummary
import com.apx5.apx5.operation.PrObserver
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.ui.adapter.PrCentralAdapter
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.dialogs.DialogSeasonChange
import com.apx5.apx5.ui.dialogs.DialogTeamDetail
import com.apx5.apx5.ui.listener.PrSingleClickListener
import com.apx5.apx5.ui.utilities.PrUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * SeasonsFragment
 */

@AndroidEntryPoint
class SeasonsFragment : BaseFragment<FragmentSeasonBinding>() {

    @Inject
    lateinit var prPreference: PrPreference

    @Inject
    lateinit var prUtils: PrUtils

    private val svm: SeasonsViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_season
    override fun getBindingVariable() = BR.viewModel

    private var qYear: Int = 0
    private var teamCode: String = ""
    private var vsTeamCode: String = ""
    private lateinit var prCentralAdapter: PrCentralAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamCode = prPreference.userTeam?: ""

        if (teamCode.isNotBlank()) {
            initView()
            subscriber()

            qYear = getDefaultYearOnLoad()
            recordByYear(qYear)
        } else {
            DialogActivity.dialogError(requireContext())
        }
    }

    /**
     * getDefaultYearOnLoad
     * @desc 초기 시즌연도 선택
     * @desc 설정값 우선
     */
    private fun getDefaultYearOnLoad() = if (qYear == 0) prPreference.defaultYear else qYear

    /**
     * Init View
     */
    private fun initView() {
        /* 팀리스트*/
        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        prCentralAdapter = PrCentralAdapter(
            context = requireContext(),
            viewType = PrAdapterViewType.SEASON,
            prUtils = prUtils,
            selectGame = ::fetchDetails
        )

        binding().apply {
            /* 시즌기록 리스트*/
            rvSeasonRecord.apply {
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                layoutManager = linearLayoutManager
                adapter = prCentralAdapter
            }

            /* 시즌변경 버튼*/
            btnChangeSeason.setOnClickListener(object : PrSingleClickListener() {
                override fun onSingleClick(view: View) {
                    val seasonDialog = DialogSeasonChange(
                        callback = ::selectSeason,
                        selectedYear = qYear,
                        selectType = PrDialogYearSelectType.SEASON
                    )

                    seasonDialog.show(childFragmentManager, "selectSeason")
                }
            })
        }
    }

    /**
     * cancelSpinKit
     * @desc 로딩 SpinKit 제거
     */
    private fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /**
     * selectSeason
     * @desc 시즌선택
     */
    private fun selectSeason(year: Int) {
        recordByYear(year)
        qYear = year
    }

    /**
     * fetchDetails
     * @desc 팀 선택시, 상세기록 리스트
     */
    private fun fetchDetails(year: Int, versus: String) {
        val email = prPreference.userEmail?: ""
        if (email.isNotBlank()) {
            vsTeamCode = versus
            svm.fetchDetails(email, versus, year)
        }
    }

    /**
     * showDetailLists
     * @desc 팀 선택시, 상세기록 리스트
     */
    private fun showDetailLists(plays: List<OpsTeamDetail>) {
        if (plays.isNotEmpty()) {
            val detailDialog = DialogTeamDetail(plays, vsTeamCode)
            detailDialog.show(childFragmentManager, "detailDialog")
        } else {
            DialogActivity.dialogNoRecordDetail(requireContext())
        }
    }

    /**
     * setTeamRecord
     * @desc 팀별 기록
     */
    private fun setTeamRecord(teams: List<DtTeamRecord>) {
        val seasonSummary = mutableListOf<AdtTeamLists>().also { list ->
            for (team in teams) {
                if (teamCode != team.team) {
                    list.add(
                        AdtTeamLists(
                            year = team.year,
                            team = team.team,
                            win = team.win,
                            draw = team.draw,
                            lose = team.lose,
                            rate = team.rate,
                            teamEmblem = prUtils.getDrawableByName(
                                requireContext(),
                                PrConstants.Teams.EMBLEM_PREFIX.plus(team.team)
                            )
                        )
                    )
                }
            }
        }

        prCentralAdapter.apply {
            addSeasons(seasonSummary)
            notifyDataSetChanged()
        }
    }

    /**
     * setHeaderSummary
     * @desc 상단 헤더 요약
     */
    private fun setHeaderSummary(summary: OpsTeamSummary) {
        binding().apply {
            tvBoxTitle.text = String.format(Locale.getDefault(), resources.getString(R.string.season_label), summary.year)
            tvSeasonStatic.text =
                String.format(
                    Locale.getDefault(),
                    resources.getString(R.string.w_d_l), summary.win, summary.draw, summary.lose
                )

            tvTeamName.text = PrTeam.team(teamCode).fullName
            ivTeamEmblem.setImageResource(
                prUtils.getDrawableByName(
                    requireContext(),
                    PrConstants.Teams.EMBLEM_PREFIX.plus(teamCode)
                )
            )
        }
    }

    /**
     * recordByYear
     * @desc 연도별 기록 Fetch
     */
    private fun recordByYear(year: Int) {
        val email = prPreference.userEmail?: ""

        if (email.isBlank()) {
            DialogActivity.dialogError(requireContext())
        } else {
            svm.fetchRecords(email, year)
        }
    }

    /**
     * subscriber
     */
    private fun subscriber() {
        svm.apply {
            getTeams().observe(viewLifecycleOwner, PrObserver {
                setTeamSummaryItems(it.teams)
                setHeaderSummaryItems(it.summary)
                cancelSpinKit()
            })

            getDetails().observe(viewLifecycleOwner, PrObserver {
                val games = it.games
                showDetailLists(games)
            })
        }
    }

    /**
     * setTeamSummaryItems
     * @desc 팀헤더 요약정보
     */
    private fun setTeamSummaryItems(teams: List<OpsTeamRecords>?) {
        val listTeam = mutableListOf<DtTeamRecord>().also { _list ->
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

                    _list.add(teamEntity)
                }
            } ?: emptyArray<DtTeamRecord>()
        }

        setTeamRecord(listTeam)
    }

    /**
     * setHeaderSummaryItems
     * @desc 팀헤더 요약정보
     */
    private fun setHeaderSummaryItems(summary: OpsTeamSummary?) {
        summary?.let { setHeaderSummary(it) }
    }

    companion object {
        fun newInstance() = SeasonsFragment().apply {  }
    }
}