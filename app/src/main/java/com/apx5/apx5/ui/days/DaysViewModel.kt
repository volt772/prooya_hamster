package com.apx5.apx5.ui.days

import android.app.Application
import androidx.databinding.ObservableField
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.DtDailyGame
import com.apx5.apx5.datum.pitcher.PtGetPlay
import com.apx5.apx5.datum.pitcher.PtPostPlay
import com.apx5.apx5.datum.catcher.CtGetPlay
import com.apx5.apx5.datum.catcher.CtPostPlay
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.datum.ops.OpsDailyPlay
import com.apx5.apx5.ui.utils.UiUtils
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

    private val prService = PrOps.getInstance()
    private val app: Application = getApplication()

    private var playList = mutableListOf<DtDailyGame>()
    private lateinit var _game: DtDailyGame

    val dailyGame: DtDailyGame
        get() = _game

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
        awayTeam.set(_game.awayTeam.fullName)

        /* 홈팀명*/
        homeTeam.set(_game.homeTeam.fullName)

        /* 게임상태*/
        if (_game.status == PrGameStatus.FINE) {
            gameStatus.set(
                String.format(
                    Locale.getDefault(),
                    app.resources.getString(R.string.day_game_score),
                    _game.awayScore,
                    _game.homeScore)
            )
        } else {
            gameStatus.set(_game.status.displayCode)
        }

        /* 게임일자*/
        val _playDate = UiUtils.getDateToFull(_game.playDate.toString())
        val _startTime = UiUtils.getTime(_game.startTime.toString())

//        if (_game.startTime == 0) {
//            gameDate.set(
//                String.format(
//                    Locale.getDefault(),
//                    app.resources.getString(R.string.day_game_date_single), _playDate)
//            )
//        } else {
//            gameDate.set(
//                String.format(
//                    Locale.getDefault(),
//                    app.resources.getString(R.string.day_game_date_with_starttime),
//                    _playDate,
//                    _startTime)
//            )
//        }

        /* 게임장소*/
        gameStadium.set(_game.stadium.displayName)
    }

    /* 경기정보*/
    internal fun getMyPlay(play: PtGetPlay) {
        prService.loadTodayGame(play, object: PrOpsCallBack<CtGetPlay> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtGetPlay>?) {
                responseBody?.data?.let { res ->
                    makePlayBoard(res.games)
                    getNavigator()?.cancelSpinKit()
                }
            }

            override fun onFailed(errorData: PrOpsError) {
                getNavigator()?.cancelSpinKit()
            }
        })
    }

    private fun makePlayBoard(dailyPlays: List<OpsDailyPlay>) {
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
                        awayTeam = PrTeam.getTeamByCode(awayteam),
                        homeTeam = PrTeam.getTeamByCode(hometeam),
                        playDate = playdate,
                        startTime = UiUtils.getTime(starttime.toString()),
                        stadium = PrStadium.getStadiumByCode(stadium),
                        status = PrGameStatus.getStatsByCode(getPlayStatusCode(awayscore)),
                        additionalInfo = ""
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
    internal fun saveNewPlay(play: PtPostPlay) {
        prService.postGame(play, object: PrOpsCallBack<CtPostPlay> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtPostPlay>?) {
                responseBody?.data?.let {
                    getNavigator()?.showSuccessDialog()
                }
            }

            override fun onFailed(errorData: PrOpsError) { }
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
