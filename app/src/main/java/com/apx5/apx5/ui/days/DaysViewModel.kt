package com.apx5.apx5.ui.days

import android.app.Application
import androidx.databinding.ObservableField
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.constants.PrConstants
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
    internal fun makeGameItem(game: HashMap<String, String>) {
        /* 원정팀명*/
        awayTeam.set(game[PrConstants.Game.AWAYTEAM])

        /* 홈팀명*/
        homeTeam.set(game[PrConstants.Game.HOMETEAM])

        /* 게임상태*/
        val status = game[PrConstants.Game.STATUSCODE]
        if (status != null && !status.equalsExt("")) {
            if (Integer.parseInt(status) == PrConstants.Codes.FINE) {
                gameStatus.set(
                        String.format(Locale.getDefault(),
                            getApplication<Application>().resources.getString(R.string.day_game_score),
                            game[PrConstants.Game.AWAYSCORE], game[PrConstants.Game.HOMESCORE]))
            } else {
                gameStatus.set(game[PrConstants.Game.STATUS])
            }
        }

        /* 게임일자*/
        if (game[PrConstants.Game.PLAYTIME].equalsExt("")) {
            gameDate.set(
                    String.format(Locale.getDefault(),
                        getApplication<Application>().resources.getString(R.string.day_game_date_single),
                        game[PrConstants.Game.PLAYDATE]))
        } else {
            gameDate.set(
                    String.format(Locale.getDefault(),
                        getApplication<Application>().resources.getString(R.string.day_game_date_with_starttime),
                        game[PrConstants.Game.PLAYDATE], game[PrConstants.Game.PLAYTIME]))
        }

        /* 게임장소*/
        gameStadium.set(game[PrConstants.Game.STADIUM])
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
            PrConstants.Codes.CANCELED -> PrConstants.Codes.CANCELED
            PrConstants.Codes.STANDBY -> PrConstants.Codes.STANDBY
            PrConstants.Codes.ONPLAY -> PrConstants.Codes.ONPLAY
            else -> PrConstants.Codes.FINE
        }
    }
}
