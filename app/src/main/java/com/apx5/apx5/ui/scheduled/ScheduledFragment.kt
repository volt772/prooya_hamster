package com.apx5.apx5.ui.scheduled

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import com.apx5.apx5.ProoyaClient
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrConstants.DAY
import com.apx5.apx5.constants.PrConstants.DRAWABLE
import com.apx5.apx5.constants.PrConstants.MONTH
import com.apx5.apx5.constants.PrConstants.YEAR
import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentScheduledBinding
import com.apx5.apx5.datum.DtDailyGame
import com.apx5.apx5.datum.DtResultBySide
import com.apx5.domain.ops.OpsDailyPlay
import com.apx5.apx5.ext.equalsExt
import com.apx5.apx5.ext.setVisibility
import com.apx5.apx5.operation.PrObserver
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.listener.PrSingleClickListener
import com.apx5.apx5.ui.utilities.PrUtils
import com.apx5.domain.param.GameParam
import com.apx5.domain.param.GameSaveParam
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * ScheduledFragment
 */

@AndroidEntryPoint
class ScheduledFragment : BaseFragment<FragmentScheduledBinding>() {

    @Inject
    lateinit var prPreference: PrPreference

    @Inject
    lateinit var prUtils: PrUtils

    private val svm: ScheduledViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_scheduled
    override fun getBindingVariable() = BR.viewModel

    private var selectedDate: String = ""

    private var email: String = ""
    private var teamCode: String = ""

    private lateinit var dailyGame: DtDailyGame

    private var gameList = mutableListOf<DtDailyGame>()

    /* 캘린더 핸들러 */
    private val calListener = DaysCalendar.datePickerListener(searchPlay = ::searchPlayByDate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        val queryDate = if (selectedDate.isBlank()) prUtils.today else selectedDate
        searchPlayByDate(queryDate)

        subscriber()
    }

    /**
     * searchOtherGame
     * @desc 다른 경기 검색 (캘린더)
     */
    private fun searchOtherGame() {
        DaysCalendar.datePickerDialog(requireActivity(), calListener).show()
    }

    /**
     * cancelSpinKit
     * @desc 로딩중 SpinkKit 제거
     */
    private fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /**
     * saveGameToRemote
     * @desc 경기저장 / POST
     */
    private fun saveGameToRemote() {
        val gameResult = getPlayResultByTeamSide()
        val myTeamCode = prPreference.userTeam?: ""

        svm.saveNewPlay(
            GameSaveParam(
                result = gameResult.result,
                year = prUtils.getYear(dailyGame.playDate.toString()),
                regdate = prUtils.getDateToAbbr(dailyGame.playDate.toString(), "-"),
                pid = email,
                lostscore = gameResult.lostScore,
                versus = gameResult.versus,
                myteam = myTeamCode,
                getscore = gameResult.getScore
            )
        )
    }

    /**
     * getPlayResultByTeamSide
     * @desc 홈/원정 결과 구분
     */
    private fun getPlayResultByTeamSide(): DtResultBySide {
        val isAwayTeam = teamCode.equalsExt(dailyGame.awayTeam.code)

        val awayScore = dailyGame.awayScore
        val homeScore = dailyGame.homeScore

        if (isAwayTeam) {
            /* 원정경기*/
            return DtResultBySide(
                versus = dailyGame.homeTeam.code,
                getScore = awayScore.toString(),
                lostScore = homeScore.toString(),
                result = when {
                    awayScore < homeScore -> PrResultCode.LOSE.codeAbbr
                    awayScore > homeScore -> PrResultCode.WIN.codeAbbr
                    else -> PrResultCode.DRAW.codeAbbr
                }
            )
        } else {
            /* 홈경기*/
            return DtResultBySide(
                versus = dailyGame.awayTeam.code,
                getScore = homeScore.toString(),
                lostScore = awayScore.toString(),
                result = when {
                    awayScore > homeScore -> PrResultCode.LOSE.codeAbbr
                    awayScore < homeScore -> PrResultCode.WIN.codeAbbr
                    else -> PrResultCode.DRAW.codeAbbr
                }
            )
        }
    }

    /**
     * fetchGames
     * @desc 경기 가져오기
     */
    private fun fetchGames(show: Boolean) {
        showScoreBoard(show)

        if (show) {
            /* 저장버튼 노출유무*/
            showSaveButton(dailyGame.status)

            /* 팀 엠블럼*/
            val awayTeam = dailyGame.awayTeam
            val homeTeam = dailyGame.homeTeam
            showTeamEmblem(awayTeam, homeTeam)

            makeGameItem()
        }
    }

    /**
     * showDialogForDoubleHeader
     * @desc (Dialog) 더블헤더 선택
     */
    private fun showDialogForDoubleHeader() {
        DialogActivity.dialogSelectDoubleHeader(requireContext(), ::selectMainGameOfDoubleHeader)
    }

    /**
     * selectMainGameOfDoubleHeader
     * @desc 더블헤더 선택
     */
    private fun selectMainGameOfDoubleHeader(gameNum: Int) {
        setMainGameData(gameNum)
    }

    /**
     * showSuccessDialog
     * @desc 완료 Dialog
     */
    private fun showSuccessDialog() {
        DialogActivity.dialogSaveDailyHistory(requireContext())
    }

    /**
     * initView
     */
    private fun initView() {
        email = prPreference.userEmail?: ""
        teamCode = prPreference.userTeam?: ""

        if (email.isEmpty() || teamCode.isEmpty()) {
            DialogActivity.dialogError(requireContext())
        } else {
            DaysCalendar.apply {
                todayYear = prUtils.getTodaySeparate(YEAR)
                todayMonth = prUtils.getTodaySeparate(MONTH)
                todayDay = prUtils.getTodaySeparate(DAY)
            }
        }

        binding().apply {
            btnChangeSeason.setOnClickListener(object : PrSingleClickListener() {
                override fun onSingleClick(view: View) { searchOtherGame() }
            })

            btSavePlay.setOnClickListener(object : PrSingleClickListener() {
                override fun onSingleClick(view: View) { saveGameToRemote() }
            })
        }
    }

    /**
     * searchPlayByDate
     * @desc (캘린더) 경기검색
     */
    private fun searchPlayByDate(playDate: String) {
        selectedDate = playDate
        val play = GameParam(playDate, teamCode)
        svm.fetchMyGame(play)
    }

    /**
     * showSaveButton
     * @desc 저장버튼노출 (경기종료시에만 저장)
     */
    private fun showSaveButton(status: PrGameStatus) {
        binding().btSavePlay.visibility = setVisibility(status == PrGameStatus.FINE)
    }

    /**
     * showScoreBoard
     * @desc 스코어 보드
     */
    private fun showScoreBoard(gameExist: Boolean) {
        binding().apply {
            clScoreBoard.visibility = setVisibility(gameExist)
            clNoGame.visibility = setVisibility(!gameExist)
        }
    }

    /**
     * showTeamEmblem
     * @desc 팀 엠블럼 및 팀컬러
     */
    private fun showTeamEmblem(away: PrTeam, home: PrTeam) {
        binding().apply {
            /* 엠블럼*/
            ivTeamAway.setImageResource(resources.getIdentifier(away.emblem, DRAWABLE, requireContext().packageName))
            ivTeamHome.setImageResource(resources.getIdentifier(home.emblem, DRAWABLE, requireContext().packageName))

            /* 팀컬러*/
            tvTeamAway.setBackgroundColor(Color.parseColor(away.mainColor))
            tvTeamHome.setBackgroundColor(Color.parseColor(home.mainColor))
        }
    }

    /**
     * Subscriber
     */
    private fun subscriber() {
        svm.apply {
            getTodayGame().observe(viewLifecycleOwner, PrObserver {
                makeGameBoard(it.games)
                cancelSpinKit()
            })

            postNewGame().observe(viewLifecycleOwner, PrObserver {
                if (it.result > 0) showSuccessDialog()
            })
        }
    }

    /**
     * makeGameBoard
     * @desc 게임현황
     */
    private fun makeGameBoard(dailyPlays: List<OpsDailyPlay>) {
        gameList.clear()
        for (play in dailyPlays) {
            if (play.id == 0) {
                fetchGames(false)
                return
            }

            play.run {
                gameList.add(
                    DtDailyGame(
                        gameId = id,
                        awayScore = awayscore,
                        homeScore = homescore,
                        awayTeam = PrTeam.team(awayteam),
                        homeTeam = PrTeam.team(hometeam),
                        playDate = playdate,
                        startTime = prUtils.getTime(starttime.toString()),
                        stadium = PrStadium.stadium(stadium),
                        status = PrGameStatus.status(getPlayStatusCode(awayscore)),
                        additionalInfo = "",
                        registeredGame = registedId > 0
                    )
                )
            }
        }

        if (gameList.size > 1) {
            /* 더블헤더 선택*/
            showDialogForDoubleHeader()
        } else {
            /* 일반*/
            setMainGameData()
        }
    }

    /**
     * getPlayStatusCode
     * @desc 경기 상태 코드
     */
    private fun getPlayStatusCode(code: Int) = PrGameStatus.status(code).code

    /**
     * setMainGameData
     * @desc 주 게임선택
     */
    private fun setMainGameData(gameNum: Int = 0) {
        dailyGame = gameList[gameNum]
        fetchGames(true)
    }

    /**
     * makeGameItem
     * @desc 경기 데이터
     */
    private fun makeGameItem() {
        binding().apply {
            /* 원정팀명*/
            tvTeamAway.text = dailyGame.awayTeam.fullName

            /* 홈팀명*/
            tvTeamHome.text = dailyGame.homeTeam.fullName

            /* 게임상태*/
            if (dailyGame.status == PrGameStatus.FINE) {
                tvScore.text =
                    String.format(Locale.getDefault(), ProoyaClient.appContext.resources.getString(R.string.day_game_score), dailyGame.awayScore, dailyGame.homeScore)
            } else {
                tvScore.text = dailyGame.status.displayCode
            }

            /* 게임일자*/
            val playDate = prUtils.getDateToFull(dailyGame.playDate.toString())

            if (dailyGame.startTime == "0") {
                tvPlayDate.text =
                    String.format(Locale.getDefault(), ProoyaClient.appContext.resources.getString(R.string.day_game_date_single), playDate)
            } else {
                tvPlayDate.text =
                    String.format(Locale.getDefault(), ProoyaClient.appContext.resources.getString(R.string.day_game_date_with_starttime), playDate, prUtils.getTime(dailyGame.startTime))
            }

            /* 게임장소*/
            tvStadium.text = dailyGame.stadium.displayName
        }
    }

    companion object {
        fun newInstance() = ScheduledFragment().apply {  }
    }
}