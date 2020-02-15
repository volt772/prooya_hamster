package com.apx5.apx5.ui.days

import android.app.Application
import androidx.databinding.ObservableField
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.constants.PrConstants
import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.datum.GameInfo
import com.apx5.apx5.model.RemoteService
import com.apx5.apx5.model.ResourceGame
import com.apx5.apx5.model.ResourceGetPlay
import com.apx5.apx5.model.ResourcePostPlay
import com.apx5.apx5.utils.equalsExt
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * DaysViewModel
 */

class DaysViewModel(application: Application) : BaseViewModel<DaysNavigator>(application) {
    var awayTeam = ObservableField<String>()
    var homeTeam = ObservableField<String>()
    var gameStatus = ObservableField<String>()
    var gameDate = ObservableField<String>()
    var gameStadium = ObservableField<String>()

    private val rmts: RemoteService = remoteService

    /* 경기검색 (캘린더)*/
    fun searchOtherGame() {
        getNavigator()?.searchOtherGame()
    }

    /* 경기저장 (Remote)*/
    fun saveGameToRemote() {
        getNavigator()?.saveGameToRemote()
    }

    /* 경기 데이터*/
    internal fun makeGameItem(game: GameInfo) {
        /* 원정팀명*/
        awayTeam.set(game.awayTeam.fullName)

        /* 홈팀명*/
        homeTeam.set(game.homeTeam.fullName)

        /* 게임상태*/
        if (game.status == PrGameStatus.FINE) {
            gameStatus.set(String.format(Locale.getDefault(),
                            getApplication<Application>().resources.getString(R.string.day_game_score),
                            game.awayScore, game.homeScore))
        } else {
            gameStatus.set(game.status.displayCode)
        }

        /* 게임일자*/
        if (game.playTime.equalsExt("")) {
            gameDate.set(String.format(Locale.getDefault(), getApplication<Application>().resources.getString(R.string.day_game_date_single), game.playDate))
        } else {
            gameDate.set(String.format(Locale.getDefault(), getApplication<Application>().resources.getString(R.string.day_game_date_with_starttime), game.playDate, game.playTime))
        }

        /* 게임장소*/
        gameStadium.set(game.stadium.displayName)
    }

    /* 경기정보*/
    internal fun getMyPlay(play: ResourceGetPlay) {
        rmts.getDayPlay(play)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<RemoteService.Plays>() {
            override fun onCompleted() {
                getNavigator()?.cancelSpinKit()
            }

                override fun onError(e: Throwable) { }

            override fun onNext(play: RemoteService.Plays) {
                if (play.res.id == 0)  {
                    getNavigator()?.noGameToday()
                } else {
                    val game = ResourceGame()
                    play.res.run {
                        game.gameId = id
                        game.awayScore = awayscore
                        game.homeScore = homescore
                        game.awayTeam = awayteam
                        game.homeTeam = hometeam
                        game.playDate = playdate
                        game.startTime = starttime
                        game.stadium = stadium
                        game.status = getPlayStatusCode(awayscore)
                    }

                    getNavigator()?.setRemoteGameData(game)
                }
            }
        })
    }

    /* 새기록 저장*/
    internal fun saveNewPlay(play: ResourcePostPlay) {
        rmts.saveNewPlay(play)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<RemoteService.NewPlay>() {
                override fun onCompleted() { }

                override fun onError(e: Throwable) {}

                override fun onNext(res: RemoteService.NewPlay) {
                    /* 완료 Dialog*/
                    getNavigator()?.showSuccessDialog()
                }
            })
    }

    /* 경기 상태 코드*/
    private fun getPlayStatusCode(code: Int): Int {
        return when (code) {
            999 -> PrGameStatus.CANCELED.code
            998 -> PrGameStatus.STANDBY.code
            997 -> PrGameStatus.ONPLAY.code
            else -> PrGameStatus.FINE.code
        }
    }
}
