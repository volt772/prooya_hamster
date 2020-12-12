package com.apx5.apx5.ui.statics

import android.app.Application
import androidx.databinding.ObservableField
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.DtStatics
import com.apx5.apx5.datum.catcher.CtPostStatics
import com.apx5.apx5.datum.ops.OpsAllStatics
import com.apx5.apx5.datum.ops.OpsDailyPlay
import com.apx5.apx5.datum.ops.OpsSeasonStatics
import com.apx5.apx5.datum.pitcher.PtPostStatics
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse
import java.util.*

/**
 * StaticsViewModel
 */

class StaticsViewModel(application: Application) :
    BaseViewModel<StaticsNavigator>(application) {

    private val prService = PrOps.getInstance()

    var seasonRate = ObservableField<String>()
    var seasonPlays = ObservableField<String>()
    var allRate = ObservableField<String>()
    var allPlays = ObservableField<String>()
    var seasonCount = ObservableField<String>()
    var allCount = ObservableField<String>()

    private lateinit var _gameList: List<OpsDailyPlay>
    val gameList: List<OpsDailyPlay>
        get() = _gameList


    /* 승률표기*/
    private fun getRateText(rate: Int): String {
        return String.format(Locale.getDefault(), getApplication<Application>().resources.getString(R.string.winning_rate), rate)
    }

    /* 승패무표기*/
    private fun getPlaysDetailText(win: Int, draw: Int, lose: Int): String {
        return String.format(Locale.getDefault(), getApplication<Application>().resources.getString(R.string.w_d_l), win, draw, lose)
    }

    /* 직관횟수표기 (시즌)*/
    private fun getSeasonCountText(countSeason: Int): String {
        return String.format(Locale.getDefault(), getApplication<Application>().resources.getString(R.string.seeing_count_season), countSeason)
    }

    /* 직관횟수표기 (통산)*/
    private fun getAllCountText(countAll: Int): String {
        return String.format(Locale.getDefault(), getApplication<Application>().resources.getString(R.string.seeing_count_all), countAll)
    }

    /**
     * 요약 데이터 생성 및 적용
     */
    private fun makeStaticItem(st: DtStatics) {
        /* 시즌승률(%)*/
        seasonRate.set(getRateText(st.rateSeason))

        /* 시즌전적(승패무)*/
        seasonPlays.set(getPlaysDetailText(st.countSeasonWin, st.countSeasonDraw, st.countSeasonLose))

        /* 통산승률(%)*/
        allRate.set(getRateText(st.rateAll))

        /* 통산전적(승패무)*/
        allPlays.set(getPlaysDetailText(st.countAllWin, st.countAllDraw, st.countAllLose))

        /* 직관횟수(시즌)*/
        seasonCount.set(getSeasonCountText(st.countSeason))

        /* 직관횟수(통산)*/
        allCount.set(getAllCountText(st.countAll))
    }

    /* 통계데이터 다운로드*/
    internal fun getStatics(userEmail: String) {
        prService.getStatics(PtPostStatics(userEmail), object: PrOpsCallBack<CtPostStatics> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtPostStatics>?) {
                responseBody?.data?.let { res ->
                    getNavigator()?.cancelSpinKit()
                    setTeamCode(res.team)
                    setStaticItem(res.allStatics, res.seasonStatics)
                    setTodayGame(res.todayGame)
                }
            }

            override fun onFailed(errorData: PrOpsError) { }
        })
    }

    /* 팀코드 저장*/
    private fun setTeamCode(teamCode: String) {
        getNavigator()?.saveMyTeamCode(teamCode)
    }

    /* 통계수치지정*/
    private fun setStaticItem(all: OpsAllStatics?, season: OpsSeasonStatics?) {
        if (all != null && season != null) {
            makeStaticItem(DtStatics(
                countAll = all.count,
                countAllDraw = all.draw,
                countAllLose = all.lose,
                countAllWin = all.win,
                rateAll = all.rate,
                countSeason = season.count,
                countSeasonDraw = season.draw,
                countSeasonLose = season.lose,
                countSeasonWin = season.win,
                rateSeason = season.rate
            ))
        }
    }

    /* 오늘경기*/
    private fun setTodayGame(game: List<OpsDailyPlay>?) {
        game?.let { _game ->
            val gameList = if (_game[0].id == 0) { emptyList() } else { _game }

            _gameList = gameList

            getNavigator()?.showTodayGame(gameList)
        }
    }
}
