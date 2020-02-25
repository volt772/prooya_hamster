package com.apx5.apx5.ui.recordall

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.DtAllGames
import com.apx5.apx5.model.RemoteService
import com.apx5.apx5.model.ResourceDelHistory
import com.apx5.apx5.model.ResourcePostTeams
import com.apx5.apx5.remote.RemoteHistories
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * RecordAllViewModel
 */

class RecordAllViewModel(application: Application) : BaseViewModel<RecordAllNavigator>(application) {
    private val rmts: RemoteService = remoteService

    /* 기록 삭제*/
    internal fun delHistory(play: ResourceDelHistory) {
        rmts.delHistory(play)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<RemoteService.DelPlay>() {
                override fun onCompleted() { }

                override fun onError(e: Throwable) {}

                override fun onNext(res: RemoteService.DelPlay) {
                    getNavigator()?.selectYear(play.year)
                }
            })
    }

    /* 전체 데이터*/
    internal fun getAllPlayLists(email: String, year: Int) {
        val resourcePostTeams = ResourcePostTeams(email, year)
        rmts.getHistories(resourcePostTeams)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<RemoteService.Histories>() {
                override fun onCompleted() {
                    getNavigator()?.cancelSpinKit()
                }

                override fun onError(e: Throwable) { }

                override fun onNext(plays: RemoteService.Histories) {
                    setPlayHistoryItems(plays.histories, year)
                }
            })
    }

    private fun setPlayHistoryItems(plays: List<RemoteHistories>, year: Int) {
        var listPlay = ArrayList<DtAllGames>()

        if (plays != null) {
            for (play in plays) {
                listPlay.add(
                    DtAllGames(
                        playId = play.playId,
                        playPtGet = play.ptGet,
                        playPtLost = play.ptLost,
                        playSeason = play.playSeason,
                        playDate = play.playDate,
                        playResult = play.playResult,
                        playVersus = play.playVs
                    )
                )
            }
        } else {
            listPlay = arrayListOf()
        }

        getNavigator()?.setHistory(listPlay, year)
    }
}
