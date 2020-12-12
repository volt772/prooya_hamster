package com.apx5.apx5.ui.statics

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrPrefKeys
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentStaticsBinding
import com.apx5.apx5.datum.ops.OpsDailyPlay
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utils.UiUtils.Companion.getTime
import com.apx5.apx5.utils.CommonUtils
import com.apx5.apx5.utils.equalsExt
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * StaticsFragment
 */

class StaticsFragment :
    BaseFragment<FragmentStaticsBinding, StaticsViewModel>(),
    StaticsNavigator, View.OnClickListener {

    private val staticsViewModel: StaticsViewModel by viewModel()

    override fun getLayoutId() = R.layout.fragment_statics
    override fun getBindingVariable() = BR.viewModel
    override fun getViewModel(): StaticsViewModel {
        staticsViewModel.setNavigator(this)
        return staticsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscriber()
    }

    /* UI 초기화*/
    private fun initView() {
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

    /* 오늘경기 리스트*/
    override fun showTodayGame(games: List<OpsDailyPlay>) {
        makeGameView(games.size)

        if (games.size > 1) {
            makeGameData(staticsViewModel.gameList[0])
            switchGameSelectionButton(0)
        }
    }

    /* 오늘경기 화면 분기*/
    private fun makeGameView(count: Int) {
        val isEmptyGame = (count == 0)
        binding().clTodayGame.visibility = CommonUtils.setVisibility(!isEmptyGame)
        binding().clNoGame.visibility = CommonUtils.setVisibility(isEmptyGame)
        binding().clGameSelect.visibility = CommonUtils.setVisibility(count > 1)

        binding().btnFirstGame.setOnClickListener(this)
        binding().btnSecondGame.setOnClickListener(this)
    }

    /* 게임데이터 생성*/
    private fun makeGameData(game: OpsDailyPlay) {
        val (awayTeam, homeTeam) = PrTeam.getTeamByCode(game.awayteam) to PrTeam.getTeamByCode(game.hometeam)
        val (awayScore, homeScore) = game.awayscore to game.homescore
        val stadium = PrStadium.getStadiumByCode(game.stadium)
        val startTime = getTime(game.starttime.toString())

        binding().clInform.visibility = CommonUtils.setVisibility(!(stadium.displayName.isEmpty() && startTime.isEmpty()))

        val statusLabel = if (stadium.displayName.isEmpty() && startTime.isNotEmpty()) {
            startTime
        } else if (stadium.displayName.isNotEmpty() && startTime.isEmpty()) {
            stadium.displayName
        } else {
            "${stadium.displayName} | $startTime"
        }

        val gameStatus = PrGameStatus.getStatsByCode(awayScore)

        binding().apply {
            clAwayTeam.backgroundTintList = ColorStateList.valueOf(Color.parseColor(awayTeam.mainColor))
            clHomeTeam.backgroundTintList = ColorStateList.valueOf(Color.parseColor(homeTeam.mainColor))

            ivAwayEmblem.setImageResource(resources.getIdentifier(awayTeam.emblem, "drawable", requireContext().packageName))
            ivHomeEmblem.setImageResource(resources.getIdentifier(homeTeam.emblem, "drawable", requireContext().packageName))

            tvAwayName.text = awayTeam.fullName
            tvHomeName.text = homeTeam.fullName

            tvAwayScore.text = awayScore.toString()
            tvHomeScore.text = homeScore.toString()

            tvGameStatus.text = statusLabel
            clStatus.backgroundTintList = ColorStateList.valueOf(Color.parseColor(gameStatus.color))
            tvStatusLabel.text = if (gameStatus == PrGameStatus.FINE) {
                "${gameStatus.displayCode} / ${getString(R.string.save_game)}"
            } else {
                gameStatus.displayCode
            }
        }
    }

    private fun subscriber() {
        val email = PrefManager.getInstance(requireContext()).userEmail?: ""
        if (!email.equalsExt("")) {
            getViewModel().getStatics(email)
        } else {
            DialogActivity.dialogError(requireContext())
        }
    }

    /* 더블헤더 경기 선택 스타일*/
    private fun switchGameSelectionButton(idx: Int) {
        if (idx == 0) {
            binding().btnFirstGame.apply {
                background = ContextCompat.getDrawable(requireActivity(), ButtonSelection.SELECTED.drawable)
                setTextAppearance(ButtonSelection.SELECTED.style)
            }
            binding().btnSecondGame.apply {
                background = ContextCompat.getDrawable(requireActivity(), ButtonSelection.DESELECTED.drawable)
                setTextAppearance(ButtonSelection.DESELECTED.style)
            }
        } else {
            binding().btnFirstGame.apply {
                background = ContextCompat.getDrawable(requireActivity(), ButtonSelection.DESELECTED.drawable)
                setTextAppearance(ButtonSelection.DESELECTED.style)
            }
            binding().btnSecondGame.apply {
                background = ContextCompat.getDrawable(requireActivity(), ButtonSelection.SELECTED.drawable)
                setTextAppearance(ButtonSelection.SELECTED.style)
            }
        }
    }

    companion object {
        fun newInstance(): StaticsFragment {
            val args = Bundle()
            val fragment = StaticsFragment()
            fragment.arguments = args
            return fragment
        }

        enum class ButtonSelection(val drawable: Int, val style: Int) {
            SELECTED(R.drawable.btn_game_select_fill_round, R.style.TodayGameSelectLabel),
            DESELECTED(R.drawable.btn_game_select_round, R.style.TodayGameSelectNormalLabel)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_first_game -> {
                makeGameData(staticsViewModel.gameList[0])
                switchGameSelectionButton(0)
            }
            R.id.btn_second_game -> {
                makeGameData(staticsViewModel.gameList[1])
                switchGameSelectionButton(1)
            }
        }
    }
}