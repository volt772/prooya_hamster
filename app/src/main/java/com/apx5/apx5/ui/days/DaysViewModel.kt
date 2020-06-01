package com.apx5.apx5.ui.days

import android.app.Application
import androidx.databinding.ObservableField
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.DtDailyGame
import com.apx5.apx5.network.PrApi
import com.apx5.apx5.model.ResourceGetPlay
import com.apx5.apx5.model.ResourcePostPlay
import com.apx5.apx5.remote.RemoteDailyPlay
import com.apx5.apx5.ui.utils.UiUtils
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * DaysViewModel
 */

class DaysViewModel(application: Application) :
    BaseViewModel<DaysNavigator>(application) {

    var awayTeam = ObservableField<String>()
    var homeTeam = ObservableField<String>()
    var gameStatus = ObservableField<String>()
    var gameDate = ObservableField<String>()
    var gameStadium = ObservableField<String>()

    private val app: Application = getApplication()

    private var playList = mutableListOf<DtDailyGame>()
    private lateinit var _game: DtDailyGame

    val dailyGame: DtDailyGame
        get() = _game

    private val rmts: PrApi = remoteService

    /* 경기검색 (캘린더)*/
    fun searchOtherGame() {
        getNavigator()?.searchOtherGame()
    }

    /* 경기저장 (Remote)*/
    fun saveGameToRemote() {
        getNavigator()?.saveGameToRemote()
    }

    /* 경기 데이터*/
    internal fun makeGameItem() {
        /* 원정팀명*/
        awayTeam.set(PrTeam.getTeamByCode(_game.awayTeam).fullName)

        /* 홈팀명*/
        homeTeam.set(PrTeam.getTeamByCode(_game.homeTeam).fullName)

        /* 게임상태*/
        val status = PrGameStatus.getStatsByCode(_game.status)
        if (status == PrGameStatus.FINE) {
            gameStatus.set(
                String.format(
                    Locale.getDefault(),
                    app.resources.getString(R.string.day_game_score),
                    _game.awayScore,
                    _game.homeScore)
            )
        } else {
            gameStatus.set(status.displayCode)
        }

        /* 게임일자*/
        val _playDate = UiUtils.getDateToFull(_game.playDate.toString())
        val _startTime = UiUtils.getTime(_game.startTime.toString())

        if (_game.startTime == 0) {
            gameDate.set(
                String.format(
                    Locale.getDefault(),
                    app.resources.getString(R.string.day_game_date_single), _playDate)
            )
        } else {
            gameDate.set(
                String.format(
                    Locale.getDefault(),
                    app.resources.getString(R.string.day_game_date_with_starttime),
                    _playDate,
                    _startTime)
            )
        }

        /* 게임장소*/
        gameStadium.set(PrStadium.getStadiumByCode(_game.stadium).displayName)
    }

    /* 경기정보*/
    internal fun getMyPlay(play: ResourceGetPlay) {
        rmts.getDayPlay(play)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<PrApi.Plays>() {
            override fun onCompleted() {
                getNavigator()?.cancelSpinKit()
            }

            override fun onError(e: Throwable) {
                getNavigator()?.cancelSpinKit()
            }

            override fun onNext(play: PrApi.Plays) {
                makePlayBoard(play.res)
            }
        })
    }

    private fun makePlayBoard(dailyPlays: List<RemoteDailyPlay>) {
        playList.clear()
        for (play in dailyPlays) {
            if (play.id == 0) {
                getNavigator()?.setRemoteGameData(false)
                break
            }

            play.run {
                playList.add(
                    DtDailyGame(
                        gameId = id,
                        awayScore = awayscore,
                        homeScore = homescore,
                        awayTeam = awayteam,
                        homeTeam = hometeam,
                        playDate = playdate,
                        startTime = starttime,
                        stadium = stadium,
                        status = getPlayStatusCode(awayscore)
                    )
                )
            }
        }

        if (playList.size > 1) {
            /* 더블헤더 선택*/
            getNavigator()?.showDialogForDoubleHeader()
        } else {
            /* 일반*/
            setMainGameData()
        }
    }

    /* 새기록 저장*/
    internal fun saveNewPlay(play: ResourcePostPlay) {
        rmts.saveNewPlay(play)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<PrApi.NewPlay>() {
                override fun onCompleted() { }

                override fun onError(e: Throwable) {}

                override fun onNext(res: PrApi.NewPlay) {
                    /* 완료 Dialog*/
                    getNavigator()?.showSuccessDialog()
                }
            })
    }

    /* 주 게임선택*/
    fun setMainGameData(gameNum: Int = 0) {
        _game = playList[gameNum]
        getNavigator()?.setRemoteGameData(true)
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
