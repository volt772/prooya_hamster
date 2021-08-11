package com.apx5.apx5.repository

import com.apx5.apx5.datum.catcher.CtGetPlay
import com.apx5.apx5.datum.catcher.CtPostPlay
import com.apx5.apx5.datum.pitcher.*
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.network.response.MkResponse
import retrofit2.http.Body
import retrofit2.http.POST

class PrRepository(private val prApi: PrApi) {
    suspend fun getServerStatus() = prApi.getServerStatus()
    suspend fun getStatics(param: PtPostStatics) = prApi.getStatics2(param)
    suspend fun getRecordByTeams(param: PtPostTeams) = prApi.getRecordByTeams2(param)
    suspend fun getRecordDetail(param: PtGetRecordDetail) = prApi.getRecordDetail2(param)
    suspend fun getHistories(param: PtPostTeams) = prApi.getHistories2(param)
    suspend fun delHistory(param: PtDelHistory) = prApi.delHistory2(param)
    suspend fun getDayPlay(param: PtGetPlay) = prApi.getDayPlay2(param)
    suspend fun postNewGame(param: PtPostPlay) = prApi.saveNewGame2(param)
}
