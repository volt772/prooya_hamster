package com.apx5.apx5.ui.statics

import android.app.Application
import androidx.databinding.ObservableField
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.db.entity.PrPlayEntity
import com.apx5.apx5.db.entity.PrStaticEntity
import com.apx5.apx5.model.RemoteService
import com.apx5.apx5.model.ResourcePostStatics
import com.apx5.apx5.ui.utils.UiUtils
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * StaticsViewModel
 */

class StaticsViewModel(application: Application) : BaseViewModel<StaticsNavigator>(application) {

    private val rmts: RemoteService = remoteService
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
    private fun makeStaticRecentPlay(plays: List<PrPlayEntity>) {
        if (plays.isNotEmpty()) {
            val pl = plays[0]

            /* 최근직관팀(팀명)*/
            recentPlayTeam.set(getRecentPlayTeamText(pl.playVersus))

            /* 최근직관날짜(날짜)*/
            recentPlay.set(getRecentPlayDateText(pl.playDate))
        } else {
            recentPlayTeam.set(getApplication<Application>().resources.getString(R.string.empty))
            recentPlay.set("-")
        }
    }

    /**
     * 요약 데이터 생성 및 적용
     */
    private fun makeStaticItem(st: PrStaticEntity) {
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
        val resourcePostStatics = ResourcePostStatics(userEmail)

        rmts.getStatics(resourcePostStatics)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<RemoteService.Statics>() {
                override fun onCompleted() {
                    getNavigator()?.cancelSpinKit()
                }

                override fun onError(e: Throwable) { }

                override fun onNext(statics: RemoteService.Statics) {
                    /* 요약 데이터 생성*/
                    setStaticItem(statics.res.allStatics, statics.res.seasonStatics)
                    setRecentPlaysItem(statics.res.recentPlays)
                }
            })
    }

    private fun setStaticItem(all: HashMap<String, Int>, season: HashMap<String, Int>) {
        val prStaticEntity = PrStaticEntity()
        prStaticEntity.countAll = all["count"]?: 0
        prStaticEntity.countAllDraw = all["draw"]?: 0
        prStaticEntity.countAllLose = all["lose"]?: 0
        prStaticEntity.countAllWin = all["win"]?: 0
        prStaticEntity.rateAll = all["rate"]?: 0

        prStaticEntity.countSeason = season["count"]?: 0
        prStaticEntity.countSeasonDraw = season["draw"]?: 0
        prStaticEntity.countSeasonLose = season["lose"]?: 0
        prStaticEntity.countSeasonWin = season["win"]?: 0
        prStaticEntity.rateSeason = season["rate"]?: 0

        makeStaticItem(prStaticEntity)
    }

    private fun setRecentPlaysItem(recentPlays: List<HashMap<String, String>>) {
        var listPlay = ArrayList<PrPlayEntity>()

        if (recentPlays != null) {
            for (play in recentPlays) {
                val playEntity = PrPlayEntity()
                playEntity.playId = play[PrConstants.Play.ID]?: ""
                playEntity.playPtGet = play[PrConstants.Play.GAIN]?: ""
                playEntity.playPtLost = play[PrConstants.Play.LOST]?: ""
                playEntity.playSeason = play[PrConstants.Play.SEASON]?: ""
                playEntity.playDate = play[PrConstants.Play.DATE]?: ""
                playEntity.playResult = play[PrConstants.Play.RESULT]?: ""
                playEntity.playVersus = play[PrConstants.Play.VERSUS]?: ""

                listPlay.add(playEntity)
            }
        } else {
            listPlay = arrayListOf()
        }

        makeStaticRecentPlay(listPlay)
        getNavigator()?.showRecentPlayList(listPlay)
    }
}
