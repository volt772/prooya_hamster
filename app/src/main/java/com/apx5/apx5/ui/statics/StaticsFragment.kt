package com.apx5.apx5.ui.statics

import android.app.Activity
import android.content.Intent
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
import com.apx5.apx5.datum.DtDailyGame
import com.apx5.apx5.datum.DtStatics
import com.apx5.apx5.datum.ops.OpsDailyPlay
import com.apx5.apx5.datum.ops.OpsUser
import com.apx5.apx5.datum.pitcher.PtPostPlay
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.calendar.DayPickerActivity
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utils.UiUtils
import com.apx5.apx5.ui.utils.UiUtils.Companion.getPlayResultByTeamSide
import com.apx5.apx5.ui.utils.UiUtils.Companion.getTime
import com.apx5.apx5.utils.CommonUtils
import com.apx5.apx5.utils.CommonUtils.getBackgroundTintList
import com.apx5.apx5.utils.CommonUtils.getDrawable
import com.apx5.apx5.utils.equalsExt
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * StaticsFragment
 */

class StaticsFragment :
    BaseFragment<FragmentStaticsBinding, StaticsViewModel>(),
    StaticsNavigator, View.OnClickListener {

    private val svm: StaticsViewModel by viewModel()

    private lateinit var dailyGame: DtDailyGame
    private var userEmail: String = ""

    override fun getLayoutId() = R.layout.fragment_statics
    override fun getBindingVariable() = BR.viewModel
    override fun getViewModel(): StaticsViewModel {
        svm.setNavigator(this)
        return svm
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userEmail = PrefManager.getInstance(requireContext()).userEmail?: ""

        initView()
        subscriber()
    }

    /* UI 초기화*/
    private fun initView() {
        binding().btnFirstGame.setOnClickListener(this)
        binding().btnSecondGame.setOnClickListener(this)
        binding().btnGameSelect.setOnClickListener(this)
        binding().btnSelectDay.setOnClickListener(this)
    }

    /* 사용자 정보 저장*/
    override fun saveUserInfo(user: OpsUser) {
        user.run {
            PrefManager.getInstance(requireActivity()).setString(PrPrefKeys.MYTEAM, team)
            PrefManager.getInstance(requireActivity()).setInt(PrPrefKeys.MY_ID, userId)
        }
    }

    /* SpinKit 제거*/
    override fun cancelSpinKit() {
        binding().clLoading.visibility = View.GONE
    }

    /* 오늘경기 리스트*/
    override fun showTodayGame(games: List<OpsDailyPlay>) {
        makeGameViewByCount(games.size)

        if (games.isNotEmpty()) {
            makeGameData(svm.gameList[0])

            if (games.size > 1) {
                switchGameSelectionButton(0)
            }
        }
    }

    /**
     * 통계 수치값 지정
     */
    override fun setDatumChart(statics: DtStatics) {
        binding().apply {
            pcRecord.setProgress(statics.rateAll.toFloat(), true)
            /* 통산전적*/
            tvLeftRecord.text = String.format(
                resources.getString(R.string.w_d_l),
                    statics.countAllWin, statics.countAllDraw, statics.countAllLose
            )
            /* 통산직관횟수*/
            tvRightRecord.text = String.format(
                resources.getString(R.string.seeing_count_season),
                    statics.countAll
            )
        }
    }

    /* 오늘경기 화면 분기*/
    private fun makeGameViewByCount(count: Int) {
        val isEmptyGame = (count == 0)
        binding().clTodayGame.visibility = CommonUtils.setVisibility(!isEmptyGame)
        binding().clNoGame.visibility = CommonUtils.setVisibility(isEmptyGame)
        binding().clGameSelect.visibility = CommonUtils.setVisibility(count > 1)
    }

    private fun makeGameView(game: DtDailyGame) {
        dailyGame = game
        binding().apply {
            /* 원정팀, 홈팀 카드 색상*/
            clAwayTeam.backgroundTintList = getBackgroundTintList(game.awayTeam.mainColor)
            clHomeTeam.backgroundTintList = getBackgroundTintList(game.homeTeam.mainColor)

            /* 원정팀, 홈팀 엠블럼*/
            ivAwayEmblem.setImageResource(getDrawable(requireContext(), game.awayTeam.emblem))
            ivHomeEmblem.setImageResource(getDrawable(requireContext(), game.homeTeam.emblem))

            /* 원정팀, 홈팀 이름*/
            tvAwayName.text = game.awayTeam.fullName
            tvHomeName.text = game.homeTeam.fullName

            /* 원정팀, 홈팀 경기점수*/
            val scoreVisibility = CommonUtils.setVisibility(game.status == PrGameStatus.FINE)
            tvHomeScore.visibility = scoreVisibility
            tvAwayScore.visibility = scoreVisibility

            tvAwayScore.text = game.awayScore.toString()
            tvHomeScore.text = game.homeScore.toString()

            /* 추가정보 : 경기장 | 경기시작시간*/
            clInform.visibility = CommonUtils.setVisibility(
                    !(game.stadium.displayName.isEmpty() && game.startTime.isEmpty())
            )
            tvGameStatus.text = game.additionalInfo
        }
    }

    /* 게임데이터 생성*/
    private fun makeGameData(game: OpsDailyPlay) {
        val (awayTeam, homeTeam) = PrTeam.getTeamByCode(game.awayteam) to PrTeam.getTeamByCode(game.hometeam)
        val (awayScore, homeScore) = game.awayscore to game.homescore
        val stadium = PrStadium.getStadiumByCode(game.stadium)
        val startTime = getTime(game.starttime.toString())

        val additionalInfo = if (stadium.displayName.isEmpty() && startTime.isNotEmpty()) {
            startTime
        } else if (stadium.displayName.isNotEmpty() && startTime.isEmpty()) {
            stadium.displayName
        } else {
            "${stadium.displayName} | $startTime"
        }

        val gameStatus = PrGameStatus.getStatsByCode(awayScore)

        makeGameView(
                DtDailyGame(
                        gameId = game.id,
                        awayTeam = awayTeam,
                        homeTeam = homeTeam,
                        awayScore = awayScore,
                        homeScore = homeScore,
                        playDate = game.playdate,
                        stadium = stadium,
                        startTime = startTime,
                        status = gameStatus,
                        additionalInfo = additionalInfo,
                        registedGame = (game.registedId > 0)
                )
        )
    }

    private fun subscriber() {
        if (!userEmail.equalsExt("")) {
            getViewModel().getStatics(userEmail)
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

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn_first_game -> {
                makeGameData(svm.gameList[0])
                switchGameSelectionButton(0)
            }
            R.id.btn_second_game -> {
                makeGameData(svm.gameList[1])
                switchGameSelectionButton(1)
            }
            R.id.btn_game_select -> {
                if (dailyGame.status != PrGameStatus.FINE) {
                    DialogActivity.dialogCannotRegist(requireContext())
                } else {
                    if (dailyGame.registedGame) {
                        DialogActivity.dialogAlreadyRegistedGame(requireContext())
                    } else {
                        val teamCode = PrefManager.getInstance(requireContext()).userTeam ?: ""
                        val newGame = getPlayResultByTeamSide(dailyGame, teamCode)

                        svm.saveNewPlay(
                            PtPostPlay(
                                result = newGame.result,
                                year = UiUtils.getYear(dailyGame.playDate.toString()),
                                regdate = UiUtils.getDateToAbbr(dailyGame.playDate.toString(), "-"),
                                pid = userEmail,
                                lostscore = newGame.lostScore,
                                versus = newGame.versus,
                                myteam = teamCode,
                                getscore = newGame.getScore
                            )
                        )
                    }
                }
            }
            R.id.btn_select_day -> {
                val daySelectIntent = DayPickerActivity.newIntent(requireContext())
                startActivityForResult(daySelectIntent, daySelectIntentCode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let {
            if (resultCode != Activity.RESULT_OK) return

            when (requestCode) {
//                MpEasIntentCode.RANGE_PICKER.code -> {
//                    val sDate = data.getSerializableExtra(MpIntent.Eas.SELECTED_START_DAY) as DateTime
//                    val eDate = data.getSerializableExtra(MpIntent.Eas.SELECTED_END_DAY) as DateTime
//
//                    searchUtils.setDates(sDate, eDate)
//
//                    /* 기간 선택 후, 검색수행*/
//                    submitQuery(typedSearchKeyword)
//                }
//                else -> { }
            }
        }
    }

    /* 완료 Dialog*/
    override fun showSuccessDialog() {
        DialogActivity.dialogSaveDailyHistory(requireContext())
        getViewModel().getStatics(userEmail)
    }

    companion object {
        const val daySelectIntentCode = 60001

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
}