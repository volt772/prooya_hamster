package com.apx5.apx5.ui.days

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.ProoyaClient.Companion.appContext
import com.apx5.apx5.R
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.base.BaseViewModel2
import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.DtDailyGame
import com.apx5.apx5.datum.catcher.CtGetPlay
import com.apx5.apx5.datum.catcher.CtPostPlay
import com.apx5.apx5.datum.catcher.CtPostStatics
import com.apx5.apx5.datum.ops.OpsDailyPlay
import com.apx5.apx5.datum.pitcher.PtGetPlay
import com.apx5.apx5.datum.pitcher.PtPostPlay
import com.apx5.apx5.datum.pitcher.PtPostStatics
import com.apx5.apx5.network.operation.PrOps
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.repository.PrRepository
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.coroutines.launch
import java.util.*

/**
 * DaysViewModel
 */

class DaysViewModel(
    private val prRepository: PrRepository
) : BaseViewModel2<DaysNavigator>()  {

    var awayTeam = ObservableField<String>()
    var homeTeam = ObservableField<String>()
    var gameStatus = ObservableField<String>()
    var gameDate = ObservableField<String>()
    var gameStadium = ObservableField<String>()

    private val todayGame = MutableLiveData<PrResource<CtGetPlay>>()
    private val postGame = MutableLiveData<PrResource<CtPostPlay>>()

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
                    appContext.resources.getString(R.string.day_game_score),
                    _game.awayScore,
                    _game.homeScore)
            )
        } else {
            gameStatus.set(_game.status.displayCode)
        }

        /* 게임일자*/
        val playDate = UiUtils.getDateToFull(_game.playDate.toString())

        if (_game.startTime == "0") {
            gameDate.set(
                String.format(
                    Locale.getDefault(),
                    appContext.resources.getString(R.string.day_game_date_single), playDate)
            )
        } else {
            gameDate.set(
                String.format(
                    Locale.getDefault(),
                    appContext.resources.getString(R.string.day_game_date_with_starttime),
                    playDate,
                    UiUtils.getTime(_game.startTime)
                )
            )
        }

        /* 게임장소*/
        gameStadium.set(_game.stadium.displayName)
    }

    /* 경기정보*/
    internal fun getMyPlay(play: PtGetPlay) {
        viewModelScope.launch {
            todayGame.postValue(PrResource.loading(null))
            try {
                val result = prRepository.getDayPlay(play)
                makePlayBoard(result.data?.games?: emptyList())
                getNavigator()?.cancelSpinKit()
            } catch (e: Exception) {
                todayGame.postValue(PrResource.error("Fetch Today Game Error", null))
                getNavigator()?.cancelSpinKit()
            }
        }
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
                        additionalInfo = "",
                        registedGame = registedId > 0
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
        viewModelScope.launch {
            postGame.postValue(PrResource.loading(null))
            try {
                prRepository.postNewGame(play)
                getNavigator()?.showSuccessDialog()
            } catch (e: Exception) {
                postGame.postValue(PrResource.error("Post Today Game Error", null))
            }
        }
    }

    /* 주 게임선택*/
    fun setMainGameData(gameNum: Int = 0) {
        _game = playList[gameNum]
        getNavigator()?.setRemoteGameData(true)
    }

    /* 경기 상태 코드*/
    private fun getPlayStatusCode(code: Int): Int {
        return PrGameStatus.getStatsByCode(code).code
    }
}