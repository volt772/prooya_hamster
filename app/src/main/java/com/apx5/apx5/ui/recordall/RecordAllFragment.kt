package com.apx5.apx5.ui.recordall

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apx5.apx5.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.*
import com.apx5.apx5.databinding.FragmentRecordAllBinding
import com.apx5.apx5.datum.DtAllGames
import com.apx5.apx5.datum.adapter.AdtGames
import com.apx5.apx5.datum.adapter.AdtPlayDelTarget
import com.apx5.apx5.datum.ops.OpsHistories
import com.apx5.apx5.datum.pitcher.PtDelHistory
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.adapter.PlayItemsAdapter
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.dialogs.DialogSeasonChange
import com.apx5.apx5.ui.utils.UiUtils
import com.apx5.apx5.utils.CommonUtils
import com.apx5.apx5.utils.equalsExt
import java.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * RecordAllFragment
 */

class RecordAllFragment :
    BaseFragment<FragmentRecordAllBinding>(),
    RecordAllNavigator
{

    private var selectedYear: Int = 0

    private val ravm: RecordAllViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_record_all
    override fun getBindingVariable() = BR.viewModel

    private lateinit var playItemsAdapter: PlayItemsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ravm.setNavigator(this)

        initView()

        selectedYear = if (selectedYear == 0) UiUtils.currentYear else selectedYear
        fetchHistories(selectedYear)

        subscriber()
    }

    /* 연도선택*/
    override fun selectYear(year: Int) {
        fetchHistories(year)
        selectedYear = year
    }

    /* SpinKit 제거*/
    private fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /* UI 초기화*/
    private fun initView() {
        /* Adapter*/
        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        playItemsAdapter = PlayItemsAdapter(requireContext(), PrAdapterViewType.ALL, ::delHistoryItem)

        binding().rvAllList.apply {
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            layoutManager = linearLayoutManager
            adapter = playItemsAdapter
        }

        /* 시즌 변경*/
        binding().btnChangeSeason.setOnClickListener {
            val seasonSelectDialog = DialogSeasonChange(::selectYear, selectedYear)
            seasonSelectDialog.show(childFragmentManager, "selectSeason")
        }
    }

    /* 기록삭제*/
    private fun delHistoryItem(delPlay: AdtPlayDelTarget) {
        val email = PrefManager.getInstance(requireContext()).userEmail?: ""

        if (!email.equalsExt("")) {
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

        playItemsAdapter.notifyDataSetChanged()
    }

    /* 기록 삭제*/
    private fun delHistory(delHistory: PtDelHistory) {
        ravm.requestDelHistory(delHistory, selectedYear)
    }

    /* 리스트 분기*/
    private fun isListExists(exists: Boolean) {
        binding().apply {
            clAllList.visibility = CommonUtils.setVisibility(exists)
            clEmptyList.visibility = CommonUtils.setVisibility(!exists)
        }
    }

    /* 기록 리스트 생성*/
    private fun setHistory(plays: List<DtAllGames>, year: Int) {
        binding().tvBoxTitle.text = String.format(
            Locale.getDefault(),
            resources.getString(R.string.all_label),
            year
        )

        playItemsAdapter.clearItems()

        isListExists(plays.isNotEmpty())

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
                    playResult =  PrResultCode.getResultByDisplayCode(play.playResult),
                    playSeason = play.playSeason,
                    playVersus = play.playVs,
                    stadium = PrStadium.getStadiumByCode(play.stadium).displayName
                )
            )
        }

        playItemsAdapter.notifyDataSetChanged()
    }

    private fun fetchHistories(year: Int) {
        val email = PrefManager.getInstance(requireContext()).userEmail
        email?.let { ravm.getAllPlayLists(it, year) }
    }

    /* Observers*/
    private fun subscriber() {
        ravm.getHistories().observe(viewLifecycleOwner, {
            when (it.status) {
                PrStatus.SUCCESS -> {
                    setPlayHistoryItems(it.data?.games?: emptyList())
                    cancelSpinKit()
                }
                PrStatus.LOADING,
                PrStatus.ERROR -> {}
            }
        })
    }

    private fun setPlayHistoryItems(games: List<OpsHistories>) {
        val listPlay = ArrayList<DtAllGames>()

        games.forEach { game ->
            listPlay.add(
                DtAllGames(
                    awayScore = game.awayScore,
                    awayTeam = game.awayTeam,
                    homeScore = game.homeScore,
                    homeTeam = game.homeTeam,
                    playDate = game.playDate,
                    playId = game.playId,
                    playResult = game.playResult,
                    playSeason = game.playSeason,
                    playVs = game.playVs,
                    stadium = game.stadium
                )
            )
        }

        setHistory(listPlay, selectedYear)
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