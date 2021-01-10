package com.apx5.apx5.ui.statics

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.DtStatics
import com.apx5.apx5.datum.adapter.AdtTeamWinningRate
import com.apx5.apx5.datum.catcher.CtPostStatics
import com.apx5.apx5.datum.ops.OpsAllStatics
import com.apx5.apx5.datum.ops.OpsTeamWinningRate
import com.apx5.apx5.datum.ops.OpsUser
import com.apx5.apx5.datum.pitcher.PtPostStatics
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse

/**
 * StaticsViewModel
 */

class StaticsViewModel(application: Application) :
    BaseViewModel<StaticsNavigator>(application) {

    private val prService = PrOps.getInstance()

    /* 통계데이터 다운로드*/
    internal fun getStatics(userEmail: String) {
        prService.getStatics(PtPostStatics(userEmail), object: PrOpsCallBack<CtPostStatics> {
            override fun onSuccess(
                responseCode: Int,
                responseMessage: String,
                responseBody: PrResponse<CtPostStatics>?
            ) {
                responseBody?.data?.let { statics ->
                    getNavigator()?.cancelSpinKit()
                    setTeamCode(statics.user)
                    setStaticItem(statics.allStatics)
                    setTeamAllPercentageItem(statics.teamWinningRate)
                }
            }

            override fun onFailed(errorData: PrOpsError) { }
        })
    }

    /* 팀코드 저장*/
    private fun setTeamCode(user: OpsUser?) {
        user?.let { _user ->
            getNavigator()?.saveUserInfo(_user)
        }
    }

    private fun setTeamAllPercentageItem(teamData: OpsTeamWinningRate?) {
        val teams = mutableListOf<AdtTeamWinningRate>()
        teamData?.let { _teamData ->
            val list = listOf(
                AdtTeamWinningRate(PrTeam.DSB, _teamData.dsb),
                AdtTeamWinningRate(PrTeam.HHE, _teamData.hhe),
                AdtTeamWinningRate(PrTeam.KAT, _teamData.kat),
                AdtTeamWinningRate(PrTeam.KTW, _teamData.ktw),
                AdtTeamWinningRate(PrTeam.LGT, _teamData.lgt),
                AdtTeamWinningRate(PrTeam.LTG, _teamData.ltg),
                AdtTeamWinningRate(PrTeam.NCD, _teamData.ncd),
                AdtTeamWinningRate(PrTeam.NXH, _teamData.nxh),
                AdtTeamWinningRate(PrTeam.SKW, _teamData.skw),
                AdtTeamWinningRate(PrTeam.SSL, _teamData.ssl)
            )

            teams.addAll(list)

            val sorted = teams.sortedWith(compareByDescending { it.winningRate })
            getNavigator()?.setTeamWinningRate(sorted)
        }
    }

    /* 통계수치지정*/
    private fun setStaticItem(statics: OpsAllStatics?) {
        statics?.let { _statics ->
            getNavigator()?.setDatumChart(
                DtStatics(
                    countAll = _statics.count,
                    countAllDraw = _statics.draw,
                    countAllLose = _statics.lose,
                    countAllWin = _statics.win,
                    rateAll = _statics.rate
                )
            )
        }
    }
}
