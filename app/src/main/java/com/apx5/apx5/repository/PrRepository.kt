package com.apx5.apx5.repository

import com.apx5.apx5.datum.pitcher.*
import com.apx5.apx5.network.api.PrApiService
import javax.inject.Inject

/**
 * PrRepository
 */
class PrRepository @Inject constructor(private val prApi: PrApiService) {

    suspend fun getServerStatus() = prApi.getServerStatus()

    suspend fun getStatics(param: PtPostStatics) = prApi.getStatics2(param)

    suspend fun getRecordByTeams(param: PtPostTeams) = prApi.getRecordByTeams2(param)

    suspend fun getRecordDetail(param: PtGetRecordDetail) = prApi.getRecordDetail2(param)

    suspend fun getHistories(param: PtPostTeams) = prApi.getHistories2(param)

    suspend fun delHistory(param: PtDelHistory) = prApi.delHistory2(param)

    suspend fun getDayPlay(param: PtGetPlay) = prApi.getDayPlay2(param)

    suspend fun postNewGame(param: PtPostPlay) = prApi.saveNewGame2(param)

    suspend fun delUser(param: PtDelUser) = prApi.delUser2(param)

    suspend fun postUser(param: PtPostUser) = prApi.postUser2(param)
}
