package com.apx5.apx5.ui.days

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.library.baseAdapters.BR
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseFragment
import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrResultCode
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.databinding.FragmentDaysBinding
import com.apx5.apx5.datum.GameInfo
import com.apx5.apx5.model.ResourceGame
import com.apx5.apx5.model.ResourceGetPlay
import com.apx5.apx5.model.ResourcePostPlay
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

class DaysFragment : BaseFragment<FragmentDaysBinding, DaysViewModel>(), DaysNavigator {
    private val daysViewModel: DaysViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_days
    }

    override fun getViewModel(): DaysViewModel {
        daysViewModel.setNavigator(this)
        return daysViewModel
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    private lateinit var awayName: TextView
    private lateinit var homeName: TextView
    private lateinit var emblemAway: ImageView
    private lateinit var emblemHome: ImageView
    private lateinit var save: Button

    private lateinit var scoreLayout: CardView
    private lateinit var emptyLayout: CardView

    private var email: String = ""
    private var teamCode: String = ""

    private var resourceGame: ResourceGame
    private var todayYear: Int
    private var todayMonth: Int
    private var todayDay: Int

    init {
        todayYear = 0
        todayMonth = 0
        todayDay = 0
        resourceGame = ResourceGame()
    }


    /* 캘린더 핸들러 */
    private val listener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        var month = monthOfYear
        month += 1
        todayYear = year
        todayMonth = month
        todayDay = dayOfMonth

        val searchDate = String.format(Locale.getDefault(), "%d%02d%02d", todayYear, todayMonth, todayDay)
        searchPlayByDate(searchDate)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        searchPlayByDate(UiUtils.today)
    }

    /* 다른 경기 검색 (캘린더)*/
    override fun searchOtherGame() {
        val dialog = DatePickerDialog(requireActivity(), R.style.ProoyaDatePickerTheme, listener, todayYear, todayMonth - 1, todayDay)
        dialog.show()
    }

    /* SpinKit 제거*/
    override fun cancelSpinKit() {
        binding().skLoading.visibility = View.GONE
    }

    /* 경기저장(Remote)*/
    override fun saveGameToRemote() {
        val lostScore: Int
        val getScore: Int
        val result: String
        val playYear = UiUtils.getYear(resourceGame.playDate.toString())
        val playDate = UiUtils.getDateToAbbr(resourceGame.playDate.toString(), "-")
        val versus: String?

        val awayScore = resourceGame.awayScore
        val homeScore = resourceGame.homeScore

        if (teamCode.equalsExt(resourceGame.awayTeam)) {
            /* 원정일경우*/
            versus = resourceGame.homeTeam
            getScore = resourceGame.awayScore
            lostScore = resourceGame.homeScore
            result = if (awayScore < homeScore) "l" else if (awayScore > homeScore) "w" else "d"
        } else {
            /* 홈일경우*/
            versus = resourceGame.awayTeam
            getScore = resourceGame.homeScore
            lostScore = resourceGame.awayScore
            result = if (awayScore > homeScore) "l" else if (awayScore < homeScore) "w" else "d"
        }

        getViewModel().saveNewPlay(ResourcePostPlay(
                result, playYear, playDate, email, lostScore.toString(), versus, getScore.toString()))
    }

    /* 해당일 경기없음*/
    override fun noGameToday() {
        showScoreBoard(false)
    }

    /* 경기 가져오기(From Remote)*/
    override fun setRemoteGameData(game: ResourceGame) {
        /* 데이터 저장*/
        resourceGame = game

        /* 점수판 화면노출*/
        showScoreBoard(true)

        /* 저장버튼 노출유무*/
        showSaveButton(game.status)

        /* 팀 엠블럼*/
        showTeamEmblem(PrTeam.getTeamByCode(game.awayTeam), PrTeam.getTeamByCode(game.homeTeam))

        /* 스코어 데이터*/
        val gameInfo = GameInfo(
            awayScore = game.awayScore,
            homeScore = game.homeScore,
            awayTeam = PrTeam.getTeamByCode(game.awayTeam),
            homeTeam = PrTeam.getTeamByCode(game.homeTeam),
            playDate = UiUtils.getDateToFull(game.playDate.toString()),
            stadium = PrStadium.getStadiumByCode(game.stadium),
            playTime = UiUtils.getTime(game.startTime.toString()),
            statusCode = game.status,
            status = PrGameStatus.getStatsByCode(game.status)
        )

        getViewModel().makeGameItem(gameInfo)
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
            awayName = binding().tvTeamAway
            homeName = binding().tvTeamHome
            emblemAway = binding().ivTeamAway
            emblemHome = binding().ivTeamHome
            save = binding().btSavePlay

            scoreLayout = binding().cvScoreBoard
            emptyLayout = binding().cvEmpty

            resourceGame = ResourceGame()

            todayYear = UiUtils.getTodaySeparate("year")
            todayMonth = UiUtils.getTodaySeparate("month")
            todayDay = UiUtils.getTodaySeparate("day")
        }
    }

    /* 경기검색(캘린더)*/
    private fun searchPlayByDate(playDate: String) {
        val play = ResourceGetPlay(playDate, teamCode)
        getViewModel().getMyPlay(play)
    }

    /* 저장버튼노출 (경기종료시에만 저장)*/
    private fun showSaveButton(playStatus: Int) {
        save.visibility = CommonUtils.setVisibility(playStatus == PrResultCode.FINE.code)
    }

    /* 스코어 보드*/
    private fun showScoreBoard(gameExist: Boolean) {
        scoreLayout.visibility = CommonUtils.setVisibility(gameExist)
        emptyLayout.visibility = CommonUtils.setVisibility(!gameExist)
    }

    /* 팀 엠블럼 및 팀컬러*/
    private fun showTeamEmblem(away: PrTeam, home: PrTeam) {
        /* 엠블럼*/
        emblemAway.setImageResource(resources.getIdentifier(away.emblem, "drawable", requireContext().packageName))
        emblemHome.setImageResource(resources.getIdentifier(home.emblem, "drawable", requireContext().packageName))

        /* 팀컬러*/
        awayName.setBackgroundColor(Color.parseColor(away.mainColor))
        homeName.setBackgroundColor(Color.parseColor(home.mainColor))
    }

    companion object {

        fun newInstance(): DaysFragment {
            val args = Bundle()
            val fragment = DaysFragment()
            fragment.arguments = args
            return fragment
        }
    }
}