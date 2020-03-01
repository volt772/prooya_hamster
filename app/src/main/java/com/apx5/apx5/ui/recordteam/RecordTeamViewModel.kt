package com.apx5.apx5.ui.recordteam

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.DtTeamRecord
import com.apx5.apx5.model.RemoteService
import com.apx5.apx5.model.ResourceGetRecordDetail
import com.apx5.apx5.model.ResourcePostTeams
import com.apx5.apx5.remote.RemoteTeamRecords
import com.apx5.apx5.remote.RemoteTeamSummary
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * RecordTeamViewModel
 */

class RecordTeamViewModel(application: Application) :
    BaseViewModel<RecordTeamNavigator>(application) {

    private val rmts: RemoteService = remoteService

    /* 팀 상세 데이터*/
    internal fun getDetailList(email: String, versus: String, year: Int) {
        val resourceGetRecordDetail = ResourceGetRecordDetail(email, versus, year)

        rmts.getRecordDetail(resourceGetRecordDetail)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<RemoteService.TeamDetail>() {
                override fun onCompleted() { }

                override fun onError(e: Throwable) { }

                override fun onNext(details: RemoteService.TeamDetail) {
                    /* 상세 데이터 생성*/
                    getNavigator()?.showDetailLists(details.plays)
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
                override fun onCompleted() {
                    getNavigator()?.cancelSpinKit()
                }

                override fun onError(e: Throwable) { }

                override fun onNext(summary: RemoteService.TeamsSummary) {
                    /* 요약 데이터 생성*/
                    setTeamSummaryItems(summary.res.teams)
                    setHeaderSummary(summary.res.summary)
                }
            })
    }

    private fun setTeamSummaryItems(teams: List<RemoteTeamRecords>) {
        val listTeam = ArrayList<DtTeamRecord>()

        for (team in teams) {
            val teamEntity = DtTeamRecord(
                year = team.year,
                team = team.team,
                win = team.win,
                lose = team.lose,
                draw = team.draw,
                rate = team.rate
            )

            listTeam.add(teamEntity)
        }

        getNavigator()?.setTeamRecord(listTeam)
    }

    /* 팀헤더 요약정보*/
    private fun setHeaderSummary(summary: RemoteTeamSummary) {
        getNavigator()?.setHeaderSummary(summary)
    }
}
