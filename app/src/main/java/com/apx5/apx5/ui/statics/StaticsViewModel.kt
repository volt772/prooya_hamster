package com.apx5.apx5.ui.statics

import android.app.Application
import com.apx5.apx5.base.BaseViewModel
import com.apx5.apx5.datum.DtStatics
import com.apx5.apx5.datum.catcher.CtGetPlay
import com.apx5.apx5.datum.catcher.CtPostPlay
import com.apx5.apx5.datum.catcher.CtPostStatics
import com.apx5.apx5.datum.ops.OpsAllStatics
import com.apx5.apx5.datum.ops.OpsDailyPlay
import com.apx5.apx5.datum.ops.OpsSeasonStatics
import com.apx5.apx5.datum.ops.OpsUser
import com.apx5.apx5.datum.pitcher.PtGetPlay
import com.apx5.apx5.datum.pitcher.PtPostPlay
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

    private lateinit var _gameList: List<OpsDailyPlay>
    val gameList: List<OpsDailyPlay>
        get() = _gameList

    /* 통계데이터 다운로드*/
    internal fun getStatics(userEmail: String) {
        prService.getStatics(PtPostStatics(userEmail), object: PrOpsCallBack<CtPostStatics> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtPostStatics>?) {
                responseBody?.data?.let { res ->
                    getNavigator()?.cancelSpinKit()
                    setTeamCode(res.user)
                    setStaticItem(res.allStatics, res.seasonStatics)
                    setTodayGame(res.todayGame)
                }
            }

            override fun onFailed(errorData: PrOpsError) { }
        })
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

    /* 팀코드 저장*/
    private fun setTeamCode(user: OpsUser?) {
        user?.let { _user ->
            getNavigator()?.saveUserInfo(_user)
        }
    }

    /* 통계수치지정*/
    private fun setStaticItem(all: OpsAllStatics?, season: OpsSeasonStatics?) {
        if (all != null && season != null) {
            getNavigator()?.setDatumChart(
                DtStatics(
                    countAll = all.count,
                    countAllDraw = all.draw,
                    countAllLose = all.lose,
                    countAllWin = all.win,
                    rateAll = all.rate,
                    countSeason = season.count,
                    countSeasonDraw = season.draw,
                    countSeasonLose = season.lose,
                    countSeasonWin = season.win,
                    rateSeason = season.rate
                )
            )
        }
    }

    /* 경기정보*/
    internal fun getMyPlay(play: PtGetPlay) {
        prService.loadTodayGame(play, object: PrOpsCallBack<CtGetPlay> {
            override fun onSuccess(responseCode: Int, responseMessage: String, responseBody: PrResponse<CtGetPlay>?) {
                responseBody?.data?.let { res ->

                    setTodayGame(res.games)
                    getNavigator()?.cancelSpinKit()
                }
            }

            override fun onFailed(errorData: PrOpsError) {
                getNavigator()?.cancelSpinKit()
            }
        })
    }

    /* 오늘경기*/
    private fun setTodayGame(game: List<OpsDailyPlay>?) {
        game?.let { _game ->
            val gameList = if (_game[0].id == 0) { emptyList() } else { _game }

            _gameList = gameList

            getNavigator()?.showTodayGame(gameList)
        }
    }
}
