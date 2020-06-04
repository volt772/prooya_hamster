package com.apx5.apx5.ui.recordteam

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.DtTeamRecord
import com.apx5.apx5.datum.pitcher.PtGetRecordDetail
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.datum.catcher.CtGetRecordDetail
import com.apx5.apx5.datum.catcher.CtPostTeams
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.datum.ops.OpsTeamRecords
import com.apx5.apx5.datum.ops.OpsTeamSummary
import java.util.*

/**
 * RecordTeamViewModel
 */

class RecordTeamViewModel(application: Application) :
    BaseViewModel<RecordTeamNavigator>(application) {

    private val prService = PrOps.getInstance()

    /* 팀 상세 데이터*/
    internal fun getDetails(email: String, versus: String, year: Int) {
        prService.getRecordDetails(PtGetRecordDetail(email, versus, year), object: PrOpsCallBack<CtGetRecordDetail> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtGetRecordDetail>?) {
                responseBody?.data?.let { res ->
                    getNavigator()?.showDetailLists(res.games)
                }
            }

            override fun onFailed(errorData: PrOpsError) { }
        })
    }

    /* 팀 상세 데이터 다운로드*/
    internal fun getRecords(email: String, year: Int) {
        prService.getRecordByTeams(PtPostTeams(email, year), object: PrOpsCallBack<CtPostTeams> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtPostTeams>?) {
                responseBody?.data?.let { res ->
                    setTeamSummaryItems(res.teams)
                    setHeaderSummary(res.summary)
                    getNavigator()?.cancelSpinKit()
                }
            }

            override fun onFailed(errorData: PrOpsError) { }
        })
    }

    private fun setTeamSummaryItems(teams: List<OpsTeamRecords>) {
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
    }

    /* 팀헤더 요약정보*/
    private fun setHeaderSummary(summary: OpsTeamSummary?) {
        if (summary != null) {
            getNavigator()?.setHeaderSummary(summary)
        }
    }
}
