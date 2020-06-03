package com.apx5.apx5.ui.recordall

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.DtAllGames
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.model.ResourceDelHistory
import com.apx5.apx5.model.ResourceGetRecordDetail
import com.apx5.apx5.model.ResourcePostTeams
import com.apx5.apx5.network.dto.PrHistoriesDto
import com.apx5.apx5.network.dto.PrHistoryDelDto
import com.apx5.apx5.network.dto.PrRecordDetailDto
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.remote.RemoteHistories
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * RecordAllViewModel
 */

class RecordAllViewModel(application: Application) :
    BaseViewModel<RecordAllNavigator>(application) {

    private val prService = PrOps.getInstance()
    private val rmts: PrApi = remoteService

    /* 기록 삭제*/
    internal fun delHistory(play: ResourceDelHistory) {
        prService.deleteHistory(play, object: PrOpsCallBack<PrHistoryDelDto> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<PrHistoryDelDto>?) {
                getNavigator()?.selectYear(play.year)
            }

            override fun onFailed(errorData: PrOpsError) { }
        })

//        rmts.delHistory(play)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Subscriber<PrApi.DelPlay>() {
//                override fun onCompleted() { }
//
//                override fun onError(e: Throwable) {}
//
//                override fun onNext(res: PrApi.DelPlay) {
//                    getNavigator()?.selectYear(play.year)
//                }
//            })
    }

    /* 전체 데이터*/
    internal fun getAllPlayLists(email: String, year: Int) {
//        val resourcePostTeams = ResourcePostTeams(email, year)

        prService.getHistories(ResourcePostTeams(email, year), object: PrOpsCallBack<PrHistoriesDto> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<PrHistoriesDto>?) {
                responseBody?.data?.let { res ->
                    setPlayHistoryItems(res.games, year)
                    getNavigator()?.cancelSpinKit()
                }
            }

            override fun onFailed(errorData: PrOpsError) { }

        })
//        rmts.getHistories(resourcePostTeams)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Subscriber<PrApi.Histories>() {
//                override fun onCompleted() {
//                    getNavigator()?.cancelSpinKit()
//                }
//
//                override fun onError(e: Throwable) { }
//
//                override fun onNext(plays: PrApi.Histories) {
//                    setPlayHistoryItems(plays.histories, year)
//                }
//            })
    }

    private fun setPlayHistoryItems(plays: List<RemoteHistories>, year: Int) {
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
