package com.apx5.apx5.ui.statics

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.DtStatics
import com.apx5.apx5.datum.catcher.CtPostStatics
import com.apx5.apx5.datum.ops.OpsAllStatics
import com.apx5.apx5.datum.ops.OpsTeamAllPercentage
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
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtPostStatics>?) {
                responseBody?.data?.let { statics ->
                    getNavigator()?.cancelSpinKit()
                    setTeamCode(statics.user)
                    setStaticItem(statics.allStatics)
                    setTeamAllPercentageItem(statics.teamAllPercentage)
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

    private fun setTeamAllPercentageItem(teamData: OpsTeamAllPercentage?) {
        teamData?.let { _teamData ->

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
