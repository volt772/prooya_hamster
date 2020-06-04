package com.apx5.apx5.ui.recordall

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.DtAllGames
import com.apx5.apx5.datum.pitcher.PtDelHistory
import com.apx5.apx5.datum.pitcher.PtPostTeams
import com.apx5.apx5.datum.catcher.CtHistories
import com.apx5.apx5.datum.catcher.CtDelHistory
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.datum.ops.OpsHistories
import java.util.*

/**
 * RecordAllViewModel
 */

class RecordAllViewModel(application: Application) :
    BaseViewModel<RecordAllNavigator>(application) {

    private val prService = PrOps.getInstance()

    /* 기록 삭제*/
    internal fun delHistory(play: PtDelHistory) {
        prService.deleteHistory(play, object: PrOpsCallBack<CtDelHistory> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtDelHistory>?) {
                getNavigator()?.selectYear(play.year)
            }

            override fun onFailed(errorData: PrOpsError) { }
        })
    }

    /* 전체 데이터*/
    internal fun getAllPlayLists(email: String, year: Int) {
        prService.getHistories(PtPostTeams(email, year), object: PrOpsCallBack<CtHistories> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtHistories>?) {
                responseBody?.data?.let { res ->
                    setPlayHistoryItems(res.games, year)
                    getNavigator()?.cancelSpinKit()
                }
            }

            override fun onFailed(errorData: PrOpsError) { }
        })
    }

    private fun setPlayHistoryItems(plays: List<OpsHistories>, year: Int) {
        val listPlay = ArrayList<DtAllGames>()

        plays.forEach { play ->
            listPlay.add(
                DtAllGames(
                    playId = play.playId,
                    playPtGet = play.ptGet,
                    playPtLost = play.ptLost,
                    playSeason = play.playSeason,
                    playDate = play.playDate,
                    playResult = play.playResult,
                    playVersus = play.playVs,
                    playMyTeam = play.playMyTeam
                )
            )
        }

        getNavigator()?.setHistory(listPlay, year)
    }
}
