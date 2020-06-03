package com.apx5.apx5.ui.recordteam

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.DtTeamRecord
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.model.ResourceGetRecordDetail
import com.apx5.apx5.model.ResourcePostTeams
import com.apx5.apx5.network.dto.PrRecordDetailDto
import com.apx5.apx5.network.dto.PrRecordsDto
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.remote.RemoteTeamRecords
import com.apx5.apx5.remote.RemoteTeamSummary
import java.util.*

/**
 * RecordTeamViewModel
 */

class RecordTeamViewModel(application: Application) :
    BaseViewModel<RecordTeamNavigator>(application) {

    private val prService = PrOps.getInstance()
    private val rmts: PrApi = remoteService

    /* 팀 상세 데이터*/
    internal fun getDetails(email: String, versus: String, year: Int) {
//        val resourceGetRecordDetail = ResourceGetRecordDetail(email, versus, year)

        prService.getRecordDetails(ResourceGetRecordDetail(email, versus, year), object: PrOpsCallBack<PrRecordDetailDto> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<PrRecordDetailDto>?) {
                responseBody?.data?.let { res ->
                    getNavigator()?.showDetailLists(res.games)
                }
            }

            override fun onFailed(errorData: PrOpsError) {
            }
        })

//        rmts.getRecordDetail(resourceGetRecordDetail)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Subscriber<PrApi.TeamDetail>() {
//                override fun onCompleted() { }
//
//                override fun onError(e: Throwable) { }
//
//                override fun onNext(details: PrApi.TeamDetail) {
//                    /* 상세 데이터 생성*/
//                    getNavigator()?.showDetailLists(details.plays)
//                }
//            })
    }

    /* 팀 상세 데이터 다운로드*/
    internal fun getRecords(email: String, year: Int) {
//        val resourcePostTeams = ResourcePostTeams(email, year)

        prService.getRecordByTeams(ResourcePostTeams(email, year), object: PrOpsCallBack<PrRecordsDto> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<PrRecordsDto>?) {
                responseBody?.data?.let { res ->
                    setTeamSummaryItems(res.teams)
                    setHeaderSummary(res.summary)
                    getNavigator()?.cancelSpinKit()
                }
            }

            override fun onFailed(errorData: PrOpsError) {
            }

        })

//        rmts.getTeams(resourcePostTeams)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Subscriber<PrApi.TeamsSummary>() {
//                override fun onCompleted() {
//                    getNavigator()?.cancelSpinKit()
//                }
//
//                override fun onError(e: Throwable) { }
//
//                override fun onNext(summary: PrApi.TeamsSummary) {
//                    /* 요약 데이터 생성*/
//                    setTeamSummaryItems(summary.res.teams)
//                    setHeaderSummary(summary.res.summary)
//                }
//            })
    }

    private fun setTeamSummaryItems(teams: List<RemoteTeamRecords>) {
        val listTeam = ArrayList<DtTeamRecord>()

        teams.forEach { team ->
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
//        for (team in teams) {
//            val teamEntity = DtTeamRecord(
//                year = team.year,
//                team = team.team,
//                win = team.win,
//                lose = team.lose,
//                draw = team.draw,
//                rate = team.rate
//            )
//
//            listTeam.add(teamEntity)
//        }
//
//        getNavigator()?.setTeamRecord(listTeam)
    }

    /* 팀헤더 요약정보*/
    private fun setHeaderSummary(summary: RemoteTeamSummary?) {
        if (summary != null) {
            getNavigator()?.setHeaderSummary(summary)
        }
    }
}
