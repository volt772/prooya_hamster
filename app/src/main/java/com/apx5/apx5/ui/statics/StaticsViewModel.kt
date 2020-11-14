package com.apx5.apx5.ui.statics

import android.app.Application
import androidx.databinding.ObservableField
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.DtPlays
import com.apx5.apx5.datum.DtStatics
import com.apx5.apx5.datum.pitcher.PtPostStatics
import com.apx5.apx5.datum.catcher.CtPostStatics
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.datum.ops.OpsAllStatics
import com.apx5.apx5.datum.ops.OpsRecentPlay
import com.apx5.apx5.datum.ops.OpsSeasonStatics
import com.apx5.apx5.ui.utils.UiUtils
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
    var recentPlayTeam = ObservableField<String>()
    var recentPlay = ObservableField<String>()
    var allCount = ObservableField<String>()

    /* 승률표기*/
    private fun getRateText(rate: Int): String {
        return String.format(Locale.getDefault(), getApplication<Application>().resources.getString(R.string.winning_rate), rate)
    }

    /* 승패무표기*/
    private fun getPlaysDetailText(win: Int, draw: Int, lose: Int): String {
        return String.format(Locale.getDefault(), getApplication<Application>().resources.getString(R.string.w_d_l), win, draw, lose)
    }

    /* 최근직관팀 표기*/
    private fun getRecentPlayTeamText(versusTeam: String): String? {
        return PrTeam.getTeamByCode(versusTeam).abbrName
    }

    /* 최근직관일 표기*/
    private fun getRecentPlayDateText(regdate: String): String {
        return UiUtils.getDateToAbbr(regdate, ".")
    }

    /* 직관횟수 표기*/
    private fun getAllCountText(countAll: Int, countSeason: Int): String {
        return String.format(Locale.getDefault(), getApplication<Application>().resources.getString(R.string.seeing_count), countSeason, countAll)
    }

    /* 최근 5경기*/
    private fun makeStaticRecentPlay(plays: List<DtPlays>) {
        if (plays.isNotEmpty()) {
            val pl = plays[0]

            /* 최근직관팀(팀명)*/
            recentPlayTeam.set(getRecentPlayTeamText(pl.playVs))

            /* 최근직관날짜(날짜)*/
            recentPlay.set(getRecentPlayDateText("${pl.playDate}"))
        } else {
            recentPlayTeam.set(getApplication<Application>().resources.getString(R.string.empty))
            recentPlay.set("-")
        }
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

        /* 직관횟수(횟수)*/
        allCount.set(getAllCountText(st.countAll, st.countSeason))
    }

    /* 통계데이터 다운로드*/
    internal fun getStatics(userEmail: String) {
        prService.getStatics(PtPostStatics(userEmail), object: PrOpsCallBack<CtPostStatics> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtPostStatics>?) {
                responseBody?.data?.let { res ->
                    getNavigator()?.cancelSpinKit()
                    setTeamCode(res.team)
                    setStaticItem(res.allStatics, res.seasonStatics)
                    setRecentPlaysItem(res.recentPlays)
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

    /* 최근5경기*/
    private fun setRecentPlaysItem(recentPlays: List<OpsRecentPlay>?) {
        if (recentPlays != null) {
            val listPlay = ArrayList<DtPlays>()

            recentPlays.forEach { game ->
                listPlay.add(DtPlays(
                    awayScore = game.awayScore,
                    awayTeam = game.awayTeam,
                    homeScore = game.homeScore,
                    homeTeam = game.homeTeam,
                    playDate = game.playDate,
                    playId = game.playId,
                    playResult = game.playResult,
                    playSeason = game.playSeason,
                    playVs = game.playVs
                ))
            }

            makeStaticRecentPlay(listPlay)
            getNavigator()?.showRecentPlayList(listPlay)
        }
    }
}
