package com.apx5.apx5.ui.days

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrResultCode
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

/**
 * DaysFragment
 */

class DaysFragment :
    BaseFragment<FragmentDaysBinding, DaysViewModel>(),
    DaysNavigator {

    private var email: String = ""
    private var teamCode: String = ""


    /* 캘린더 핸들러 */
    private val calListener = DaysCalendar.datePickerListener(searchPlay = ::searchPlayByDate)

    private val dvm: DaysViewModel by viewModel()
    override fun getLayoutId() = R.layout.fragment_days
    override fun getBindingVariable() = BR.viewModel
    override fun getViewModel(): DaysViewModel {
        dvm.setNavigator(this)
        return dvm
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        searchPlayByDate(UiUtils.today)
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

        getViewModel().saveNewPlay(
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
                lostScore = homeScore.toString() ,
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
                lostScore = awayScore.toString() ,
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

            dvm.makeGameItem()
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
        getViewModel().setMainGameData(gameNum)
    }

    /* 완료 Dialog*/
    override fun showSuccessDialog() {
        DialogActivity.dialogSaveDailyHistory(requireContext())
    }

    /* UI 초기화*/
    private fun initView() {
        email = PrefManager.getInstance(requireContext()).userEmail?: ""
        teamCode = PrefManager.getInstance(requireContext()).userTeam?: ""

        if (email.equalsExt("") || teamCode.equalsExt("")) {
            DialogActivity.dialogError(requireContext())
        } else {
            DaysCalendar.todayYear = UiUtils.getTodaySeparate("year")
            DaysCalendar.todayMonth = UiUtils.getTodaySeparate("month")
            DaysCalendar.todayDay = UiUtils.getTodaySeparate("day")
        }
    }

    /* 경기검색(캘린더)*/
    private fun searchPlayByDate(playDate: String) {
        val play = PtGetPlay(playDate, teamCode)
        getViewModel().getMyPlay(play)
    }

    /* 저장버튼노출 (경기종료시에만 저장)*/
    private fun showSaveButton(status: PrGameStatus) {
        println("probe : game satsu : ${status}")
//        binding().btSavePlay.visibility =
//            CommonUtils.setVisibility(status == PrResultCode.FINE)
    }

    /* 스코어 보드*/
    private fun showScoreBoard(gameExist: Boolean) {
        binding().cvScoreBoard.visibility = CommonUtils.setVisibility(gameExist)
        binding().cvEmpty.visibility = CommonUtils.setVisibility(!gameExist)
    }

    /* 팀 엠블럼 및 팀컬러*/
    private fun showTeamEmblem(away: PrTeam, home: PrTeam) {
        /* 엠블럼*/
        binding().ivTeamAway.setImageResource(
            resources.getIdentifier(away.emblem, "drawable", requireContext().packageName))
        binding().ivTeamHome.setImageResource(
            resources.getIdentifier(home.emblem, "drawable", requireContext().packageName))

        /* 팀컬러*/
        binding().tvTeamAway.setBackgroundColor(Color.parseColor(away.mainColor))
        binding().tvTeamHome.setBackgroundColor(Color.parseColor(home.mainColor))
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