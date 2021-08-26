package com.apx5.apx5.ui.days

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import com.apx5.apx5.ProoyaClient
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentDaysBinding
import com.apx5.apx5.datum.DtDailyGame
import com.apx5.apx5.datum.ops.OpsDailyPlay
import com.apx5.apx5.datum.pitcher.PtGetPlay
import com.apx5.apx5.datum.pitcher.PtPostPlay
import com.apx5.apx5.ext.equalsExt
import com.apx5.apx5.ext.setVisibility
import com.apx5.apx5.network.operation.PrObserver
import com.apx5.apx5.storage.PrPreference
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.listener.OnSingleClickListener
import com.apx5.apx5.ui.utils.UiUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * DaysFragment
 */

@AndroidEntryPoint
class DaysFragment : BaseFragment<FragmentDaysBinding>() {

    @Inject
    lateinit var prPreference: PrPreference

    private var selectedDate: String = ""

    private var email: String = ""
    private var teamCode: String = ""

    private lateinit var dailyGame: DtDailyGame

    private var playList = mutableListOf<DtDailyGame>()

    /* 캘린더 핸들러 */
    private val calListener = DaysCalendar.datePickerListener(searchPlay = ::searchPlayByDate)

    private val dvm: DaysViewModel by viewModels()
    override fun getLayoutId() = R.layout.fragment_days
    override fun getBindingVariable() = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        val queryDate = if (selectedDate.isBlank()) UiUtils.today else selectedDate
        searchPlayByDate(queryDate)

        subscriber()
    }

    /* 다른 경기 검색 (캘린더)*/
    private fun searchOtherGame() {
        DaysCalendar.datePickerDialog(requireActivity(), calListener).show()
    }

    /* SpinKit 제거*/
    private fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /* 경기저장(Remote)*/
    private fun saveGameToRemote() {
        val gameResult = getPlayResultByTeamSide()
        val myTeamCode = prPreference.userTeam?: ""

        dvm.saveNewPlay(
            PtPostPlay(
                result = gameResult.result,
                year = UiUtils.getYear(dailyGame.playDate.toString()),
                regdate = UiUtils.getDateToAbbr(dailyGame.playDate.toString(), "-"),
                pid = email,
                lostscore = gameResult.lostScore,
                versus = gameResult.versus,
                myteam = myTeamCode,
                getscore = gameResult.getScore
            )
        )
    }

    /**
     * 홈/원정 결과 구분
     */
    private fun getPlayResultByTeamSide(): ResultBySide {
        val isAwayTeam = teamCode.equalsExt(dailyGame.awayTeam.code)

        val awayScore = dailyGame.awayScore
        val homeScore = dailyGame.homeScore

        if (isAwayTeam) {
            /* 원정경기*/
            return ResultBySide(
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
            return ResultBySide(
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

    /* 경기 가져오기(From Remote)*/
    private fun setRemoteGameData(show: Boolean) {
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

    /* 더블헤더 선택 Dialog*/
    private fun showDialogForDoubleHeader() {
        DialogActivity.dialogSelectDoubleHeader(requireContext(), ::selectMainGameOfDoubleHeader)
    }

    /* 더블헤더 선택*/
    private fun selectMainGameOfDoubleHeader(gameNum: Int) {
        setMainGameData(gameNum)
    }

    /* 완료 Dialog*/
    private fun showSuccessDialog() {
        DialogActivity.dialogSaveDailyHistory(requireContext())
    }

    /* UI 초기화*/
    private fun initView() {
        email = prPreference.userEmail?: ""
        teamCode = prPreference.userTeam?: ""

        if (email.isEmpty() || teamCode.isEmpty()) {
            DialogActivity.dialogError(requireContext())
        } else {
            DaysCalendar.apply {
                todayYear = UiUtils.getTodaySeparate("year")
                todayMonth = UiUtils.getTodaySeparate("month")
                todayDay = UiUtils.getTodaySeparate("day")
            }
        }

        binding().apply {
            btnChangeSeason.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View) { searchOtherGame() }
            })

            btSavePlay.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View) { saveGameToRemote() }
            })
        }
    }

    /* 경기검색(캘린더)*/
    private fun searchPlayByDate(playDate: String) {
        selectedDate = playDate
        val play = PtGetPlay(playDate, teamCode)
        dvm.getMyPlay(play)
    }

    /* 저장버튼노출 (경기종료시에만 저장)*/
    private fun showSaveButton(status: PrGameStatus) {
        binding().btSavePlay.visibility = setVisibility(status == PrGameStatus.FINE)
    }

    /* 스코어 보드*/
    private fun showScoreBoard(gameExist: Boolean) {
        binding().apply {
            clScoreBoard.visibility = setVisibility(gameExist)
            clNoGame.visibility = setVisibility(!gameExist)
        }
    }

    /* 팀 엠블럼 및 팀컬러*/
    private fun showTeamEmblem(away: PrTeam, home: PrTeam) {
        binding().apply {
            /* 엠블럼*/
            ivTeamAway.setImageResource(resources.getIdentifier(away.emblem, "drawable", requireContext().packageName))
            ivTeamHome.setImageResource(resources.getIdentifier(home.emblem, "drawable", requireContext().packageName))

            /* 팀컬러*/
            tvTeamAway.setBackgroundColor(Color.parseColor(away.mainColor))
            tvTeamHome.setBackgroundColor(Color.parseColor(home.mainColor))
        }
    }

    private fun subscriber() {
        dvm.apply {
            getTodayGame().observe(viewLifecycleOwner, PrObserver {
                makePlayBoard(it.games)
                cancelSpinKit()
            })

            postNewGame().observe(viewLifecycleOwner, PrObserver {
                if (it.result > 0) showSuccessDialog()
            })
        }
    }

    private fun makePlayBoard(dailyPlays: List<OpsDailyPlay>) {
        playList.clear()
        for (play in dailyPlays) {
            if (play.id == 0) {
                setRemoteGameData(false)
                return
            }

            play.run {
                playList.add(
                    DtDailyGame(
                        gameId = id,
                        awayScore = awayscore,
                        homeScore = homescore,
                        awayTeam = PrTeam.team(awayteam),
                        homeTeam = PrTeam.team(hometeam),
                        playDate = playdate,
                        startTime = UiUtils.getTime(starttime.toString()),
                        stadium = PrStadium.stadium(stadium),
                        status = PrGameStatus.status(getPlayStatusCode(awayscore)),
                        additionalInfo = "",
                        registedGame = registedId > 0
                    )
                )
            }
        }

        if (playList.size > 1) {
            /* 더블헤더 선택*/
            showDialogForDoubleHeader()
        } else {
            /* 일반*/
            setMainGameData()
        }
    }

    /* 경기 상태 코드*/
    private fun getPlayStatusCode(code: Int) = PrGameStatus.status(code).code

    /* 주 게임선택*/
    private fun setMainGameData(gameNum: Int = 0) {
        dailyGame = playList[gameNum]
        setRemoteGameData(true)
    }

    /* 경기 데이터*/
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
            val playDate = UiUtils.getDateToFull(dailyGame.playDate.toString())

            if (dailyGame.startTime == "0") {
                tvPlayDate.text =
                    String.format(Locale.getDefault(), ProoyaClient.appContext.resources.getString(R.string.day_game_date_single), playDate)
            } else {
                tvPlayDate.text =
                    String.format(Locale.getDefault(), ProoyaClient.appContext.resources.getString(R.string.day_game_date_with_starttime), playDate, UiUtils.getTime(dailyGame.startTime))
            }

            /* 게임장소*/
            tvStadium.text = dailyGame.stadium.displayName
        }
    }


    companion object {
        data class ResultBySide(
            val versus: String,
            val getScore: String,
            val lostScore: String,
            val result: String
        )

        fun newInstance() = DaysFragment().apply {  }
    }
}