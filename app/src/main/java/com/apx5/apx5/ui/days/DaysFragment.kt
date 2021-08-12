package com.apx5.apx5.ui.days

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.ProoyaClient
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment2
import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.constants.PrStatus
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentDaysBinding
import com.apx5.apx5.datum.pitcher.PtGetPlay
import com.apx5.apx5.datum.pitcher.PtPostPlay
import com.apx5.apx5.storage.PrefManager
import com.apx5.apx5.ui.dialogs.DialogActivity
import com.apx5.apx5.ui.utils.UiUtils
import com.apx5.apx5.utils.CommonUtils
import com.apx5.apx5.utils.equalsExt
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * DaysFragment
 */

class DaysFragment :
    BaseFragment2<FragmentDaysBinding>(),
    DaysNavigator {

    private var email: String = ""
    private var teamCode: String = ""

    /* 캘린더 핸들러 */
    private val calListener = DaysCalendar.datePickerListener(searchPlay = ::searchPlayByDate)

    private val dvm: DaysViewModel by viewModel()
    override fun getLayoutId() = R.layout.fragment_days
    override fun getBindingVariable() = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dvm.setNavigator(this)

        initView()
        searchPlayByDate(UiUtils.today)

        subscriber()
    }

    /* 다른 경기 검색 (캘린더)*/
    override fun searchOtherGame() {
        DaysCalendar.datePickerDialog(requireActivity(), calListener).show()
    }

    /* SpinKit 제거*/
    override fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /* 경기저장(Remote)*/
    override fun saveGameToRemote() {
        val dailyGame = dvm.dailyGame
        val gameResult = getPlayResultByTeamSide()
        val myTeamCode = PrefManager.getInstance(requireContext()).userTeam?: ""

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
        val dailyGame = dvm.dailyGame
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
    override fun setRemoteGameData(show: Boolean) {
        showScoreBoard(show)

        if (show) {
            /* 저장버튼 노출유무*/
            showSaveButton(dvm.dailyGame.status)

            /* 팀 엠블럼*/
            val awayTeam = dvm.dailyGame.awayTeam
            val homeTeam = dvm.dailyGame.homeTeam
            showTeamEmblem(awayTeam, homeTeam)

            makeGameItem()
        }
    }

    /* 더블헤더 선택 Dialog*/
    override fun showDialogForDoubleHeader() {
        DialogActivity.dialogSelectDoubleHeader(
            requireContext(),
            ::selectMainGameOfDoubleHeader
        )
    }

    /* 더블헤더 선택*/
    private fun selectMainGameOfDoubleHeader(gameNum: Int) {
        dvm.setMainGameData(gameNum)
    }

    /* 완료 Dialog*/
    override fun showSuccessDialog() {
        DialogActivity.dialogSaveDailyHistory(requireContext())
    }

    /* UI 초기화*/
    private fun initView() {
        email = PrefManager.getInstance(requireContext()).userEmail?: ""
        teamCode = PrefManager.getInstance(requireContext()).userTeam?: ""

        if (email.isEmpty() || teamCode.isEmpty()) {
            DialogActivity.dialogError(requireContext())
        } else {
            DaysCalendar.apply {
                todayYear = UiUtils.getTodaySeparate("year")
                todayMonth = UiUtils.getTodaySeparate("month")
                todayDay = UiUtils.getTodaySeparate("day")
            }
        }

        binding().btnChangeSeason.setOnClickListener { searchOtherGame() }
        binding().btSavePlay.setOnClickListener { saveGameToRemote() }
    }

    /* 경기검색(캘린더)*/
    private fun searchPlayByDate(playDate: String) {
        val play = PtGetPlay(playDate, teamCode)
        dvm.getMyPlay(play)
    }

    /* 저장버튼노출 (경기종료시에만 저장)*/
    private fun showSaveButton(status: PrGameStatus) {
        binding().btSavePlay.visibility =
            CommonUtils.setVisibility(status == PrGameStatus.FINE)
    }

    /* 스코어 보드*/
    private fun showScoreBoard(gameExist: Boolean) {
        binding().apply {
            clScoreBoard.visibility = CommonUtils.setVisibility(gameExist)
            clNoGame.visibility = CommonUtils.setVisibility(!gameExist)
        }
    }

    /* 팀 엠블럼 및 팀컬러*/
    private fun showTeamEmblem(away: PrTeam, home: PrTeam) {
        binding().apply {
            /* 엠블럼*/
            ivTeamAway.setImageResource(
                    resources.getIdentifier(away.emblem, "drawable", requireContext().packageName))
            ivTeamHome.setImageResource(
                    resources.getIdentifier(home.emblem, "drawable", requireContext().packageName))

            /* 팀컬러*/
            tvTeamAway.setBackgroundColor(Color.parseColor(away.mainColor))
            tvTeamHome.setBackgroundColor(Color.parseColor(home.mainColor))
        }
    }

    private fun subscriber() {
        dvm.getTodayGame().observe(viewLifecycleOwner, {
            when (it.status) {
                PrStatus.SUCCESS -> {
                    dvm.makePlayBoard(it.data?.games?: emptyList())
                }
                PrStatus.LOADING,
                PrStatus.ERROR -> { cancelSpinKit() }
            }
        })

        dvm.getPostGame().observe(viewLifecycleOwner, {
            when (it.status) {
                PrStatus.SUCCESS -> {
                    showSuccessDialog()
                }
                PrStatus.LOADING,
                PrStatus.ERROR -> { }
            }
        })
    }

    /* 경기 데이터*/
    private fun makeGameItem() {
        /* 원정팀명*/
        binding().tvTeamAway.text = dvm.dailyGame.awayTeam.fullName

        /* 홈팀명*/
        binding().tvTeamHome.text = dvm.dailyGame.homeTeam.fullName

        /* 게임상태*/
        if (dvm.dailyGame.status == PrGameStatus.FINE) {
            binding().tvScore.text =
                String.format(
                    Locale.getDefault(),
                    ProoyaClient.appContext.resources.getString(R.string.day_game_score),
                    dvm.dailyGame.awayScore,
                    dvm.dailyGame.homeScore)
        } else {
            binding().tvScore.text = dvm.dailyGame.status.displayCode
        }

        /* 게임일자*/
        val playDate = UiUtils.getDateToFull(dvm.dailyGame.playDate.toString())

        if (dvm.dailyGame.startTime == "0") {
            binding().tvPlayDate.text =
                String.format(
                    Locale.getDefault(),
                    ProoyaClient.appContext.resources.getString(R.string.day_game_date_single), playDate)
        } else {
            binding().tvPlayDate.text =
                String.format(
                    Locale.getDefault(),
                    ProoyaClient.appContext.resources.getString(R.string.day_game_date_with_starttime),
                    playDate,
                    UiUtils.getTime(dvm.dailyGame.startTime)
                )
        }

        /* 게임장소*/
        binding().tvStadium.text = dvm.dailyGame.stadium.displayName
    }


    companion object {
        data class ResultBySide(
            val versus: String,
            val getScore: String,
            val lostScore: String,
            val result: String
        )

        fun newInstance(): DaysFragment {
            val args = Bundle()
            val fragment = DaysFragment()
            fragment.arguments = args
            return fragment
        }
    }
}