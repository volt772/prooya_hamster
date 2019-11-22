package com.apx5.apx5.ui.recordteam

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.db.entity.PrTeamEntity
import com.apx5.apx5.model.RemoteService
import com.apx5.apx5.model.ResourceGetRecordDetail
import com.apx5.apx5.model.ResourcePostTeams
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * RecordTeamViewModel
 */

class RecordTeamViewModel(application: Application) : BaseViewModel<RecordTeamNavigator>(application) {
    private val rmts: RemoteService = remoteService

    /* 팀 상세 데이터*/
    internal fun getDetailList(email: String, versus: String, year: Int) {
        val resourceGetRecordDetail = ResourceGetRecordDetail(email, versus, year)

        rmts.getRecordDetail(resourceGetRecordDetail)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<RemoteService.TeamDetail>() {
                override fun onCompleted() {}

                override fun onError(e: Throwable) { }


                override fun onNext(details: RemoteService.TeamDetail) {
                    /* 상세 데이터 생성*/
                    getNavigator()?.showDetailLists(details.res.plays)
                }
            })
    }

    /* 팀 상세 데이터 다운로드*/
    internal fun getTeams(email: String, year: Int) {
        val resourcePostTeams = ResourcePostTeams(email, year)

        rmts.getTeams(resourcePostTeams)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<RemoteService.TeamsSummary>() {
                override fun onCompleted() {}

                override fun onError(e: Throwable) { }

                override fun onNext(summary: RemoteService.TeamsSummary) {
                    /* 요약 데이터 생성*/
                    setTeamSummaryItems(summary.res.teams)
                    setHeaderSummary(summary.res.summary)
                }
            })
    }

    private fun setTeamSummaryItems(teams: List<HashMap<String, String>>) {
        var listTeam = ArrayList<PrTeamEntity>()

        if (listTeam != null) {
            for (team in teams) {
                val teamEntity = PrTeamEntity()
                teamEntity.year = team["year"]?: ""
                teamEntity.team = team["team"]?: ""
                teamEntity.win = team["win"]?: ""
                teamEntity.lose = team["lose"]?: ""
                teamEntity.draw = team["draw"]?: ""
                teamEntity.rate = team["rate"]?: ""

                listTeam.add(teamEntity)
            }
        } else {
            listTeam = arrayListOf()
        }

        getNavigator()?.setTeamRecord(listTeam)
    }

    /* 팀헤더 요약정보*/
    private fun setHeaderSummary(summary: HashMap<String, Int>) {
        getNavigator()?.setHeaderSummary(summary)
    }
}
