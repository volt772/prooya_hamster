package com.apx5.apx5.ui.days

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apx5.apx5.base.BaseViewModel2
import com.apx5.apx5.constants.PrGameStatus
import com.apx5.apx5.constants.PrStadium
import com.apx5.apx5.constants.PrTeam
import com.apx5.apx5.datum.DtDailyGame
import com.apx5.apx5.datum.catcher.CtGetPlay
import com.apx5.apx5.datum.catcher.CtPostPlay
import com.apx5.apx5.datum.ops.OpsDailyPlay
import com.apx5.apx5.datum.pitcher.PtGetPlay
import com.apx5.apx5.datum.pitcher.PtPostPlay
import com.apx5.apx5.network.operation.PrResource
import com.apx5.apx5.repository.PrRepository
import com.apx5.apx5.ui.utils.UiUtils
import kotlinx.coroutines.launch

/**
 * DaysViewModel
 */

class DaysViewModel(
    private val prRepository: PrRepository
) : BaseViewModel2<DaysNavigator>()  {

    private val todayGame = MutableLiveData<PrResource<CtGetPlay>>()
    private val postGame = MutableLiveData<PrResource<CtPostPlay>>()

    private var playList = mutableListOf<DtDailyGame>()
    private lateinit var _game: DtDailyGame

    val dailyGame: DtDailyGame
        get() = _game

    fun getTodayGame(): LiveData<PrResource<CtGetPlay>> = todayGame
    fun getPostGame(): LiveData<PrResource<CtPostPlay>> = postGame

    /* 경기정보*/
    fun getMyPlay(play: PtGetPlay) {
        viewModelScope.launch {
            todayGame.postValue(PrResource.loading(null))
            try {
                val result = prRepository.getDayPlay(play)
                todayGame.postValue(PrResource.success(result.data))
            } catch (e: Exception) {
                todayGame.postValue(PrResource.error("Fetch Today Game Error", null))
            }
        }
    }

    fun makePlayBoard(dailyPlays: List<OpsDailyPlay>) {
        playList.clear()
        for (play in dailyPlays) {
            if (play.id == 0) {
                getNavigator()?.setRemoteGameData(false)
                return
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
    fun saveNewPlay(play: PtPostPlay) {
        viewModelScope.launch {
            postGame.postValue(PrResource.loading(null))
            try {
                val result = prRepository.postNewGame(play)
                postGame.postValue(PrResource.success(result.data))
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