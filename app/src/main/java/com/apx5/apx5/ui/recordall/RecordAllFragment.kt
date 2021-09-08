package com.apx5.apx5.ui.recordall

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.databinding.FragmentRecordAllBinding
import com.apx5.apx5.datum.adapter.AdtPlayDelTarget
import com.apx5.apx5.datum.pitcher.PtDelHistory
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.ext.setVisibility
import com.apx5.apx5.network.operation.PrObserver
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utilities.PrUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * RecordAllFragment
 */

@AndroidEntryPoint
class RecordAllFragment : BaseFragment<FragmentRecordAllBinding>() {

    @Inject
    lateinit var prPreference: PrPreference

    @Inject
    lateinit var prUtils: PrUtils

    private var selectedYear: Int = 0

    private val ravm: RecordAllViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_record_all
    override fun getBindingVariable() = BR.viewModel

    private var historiesPagingAdapter: HistoriesPagingAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        selectedYear = getDefaultYearOnLoad()
        fetchHistories(selectedYear)

        subscriber()

        collectUiState()
    }

    private fun collectUiState() {
        val email = prPreference.userEmail?: ""
        viewLifecycleOwner.lifecycleScope.launch {
            ravm.getAllHistories(PtPostTeams(email, 0)).collectLatest { histories ->
                historiesPagingAdapter?.submitData(histories)
            }
        }
        cancelSpinKit()
    }

    /**
     * 초기 시즌연도 선택
     */
    private fun getDefaultYearOnLoad() =
        if (selectedYear == 0) prPreference.defaultYear else selectedYear

    /* 연도선택*/
    private fun selectYear(year: Int) {
//        fetchHistories(year)
        selectedYear = year
    }

    /* SpinKit 제거*/
    private fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /* UI 초기화*/
    private fun initView() {
        historiesPagingAdapter = HistoriesPagingAdapter(prUtils, ::delHistoryItem)

        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding().apply {
            rvAllList.apply {
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                layoutManager = linearLayoutManager
                adapter = historiesPagingAdapter
            }

//            /* 시즌 변경*/
//            btnChangeSeason.setOnClickListener(object : PrSingleClickListener() {
//                override fun onSingleClick(view: View) {
//                    val seasonSelectDialog = DialogSeasonChange(
//                        callback = ::selectYear,
//                        selectedYear = selectedYear,
//                        selectType = PrDialogYearSelectType.RECORD_ALL
//                    )
//
//                    seasonSelectDialog.show(childFragmentManager, "selectSeason")
//                }
//            })
        }
    }

    /* 기록삭제*/
    private fun delHistoryItem(delPlay: AdtPlayDelTarget) {
        val email = prPreference.userEmail?: ""

        if (email.isNotBlank()) {
            DialogActivity.dialogHistoryDelete(
                requireContext(),
                PtDelHistory(
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
    private fun delHistory(delHistory: PtDelHistory) {
        ravm.requestDelHistory(delHistory)
    }

    /* 리스트 분기*/
    private fun isListExists(exists: Boolean) {
        binding().apply {
            clAllList.visibility = setVisibility(exists)
            clEmptyList.visibility = setVisibility(!exists)
        }
    }

//    /* 기록 리스트 생성*/
//    private fun setHistory(plays: List<DtAllGames>, year: Int) {
////        binding().tvBoxTitle.text = String.format(
////            Locale.getDefault(),
////            resources.getString(R.string.all_label),
////            year
////        )
//
//        isListExists(plays.isNotEmpty())
//
//        val playList = mutableListOf<AdtPlays>().also { list ->
//            for (play in plays) {
//                list.add(
//                    AdtPlays(
//                        awayScore = play.awayScore,
//                        awayTeam = play.awayTeam,
//                        awayEmblem = PrTeam.team(play.awayTeam),
//                        homeScore = play.homeScore,
//                        homeTeam = play.homeTeam,
//                        homeEmblem = PrTeam.team(play.homeTeam),
//                        playDate = "${play.playDate}",
//                        playId = play.playId,
//                        playResult =  PrResultCode.getResultByDisplayCode(play.playResult),
//                        playSeason = play.playSeason,
//                        playVersus = play.playVs,
//                        stadium = PrStadium.stadium(play.stadium).abbrName
//                    )
//                )
//            }
//        }
//    }

    private fun fetchHistories(year: Int) {
        prPreference.userEmail?.let { ravm.getAllPlayLists(it, year) }

        val email = prPreference.userEmail?: ""
        viewLifecycleOwner.lifecycleScope.launch {
            ravm.getAllHistories(PtPostTeams(email, 0)).collectLatest { histories ->
                historiesPagingAdapter?.submitData(histories)
            }
        }
        cancelSpinKit()
    }

    /* Observers*/
    private fun subscriber() {
        ravm.apply {
            getDelResult().observe(viewLifecycleOwner, PrObserver {
                if (it.count == 1) {
                    historiesPagingAdapter?.refresh()
                }
            })
        }
    }

//    private fun setPlayHistoryItems(games: List<OpsHistories>) {
//        val listPlay = ArrayList<DtAllGames>()
//
//        games.forEach { game ->
//            listPlay.add(
//                DtAllGames(
//                    awayScore = game.awayScore,
//                    awayTeam = game.awayTeam,
//                    homeScore = game.homeScore,
//                    homeTeam = game.homeTeam,
//                    playDate = game.playDate,
//                    playId = game.playId,
//                    playResult = game.playResult,
//                    playSeason = game.playSeason,
//                    playVs = game.playVs,
//                    stadium = game.stadium
//                )
//            )
//        }
//
//        setHistory(listPlay, selectedYear)
//    }

    companion object {
        fun newInstance() = RecordAllFragment().apply { }
    }
}