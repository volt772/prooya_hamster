package com.apx5.apx5.network.api

import com.apx5.apx5.datum.pitcher.*
import javax.inject.Inject

/**
 * PrApiHelper
 */
class PrApiHelper @Inject constructor(private val prApi: PrApiService) {
    suspend fun getServerStatus() = prApi.getServerStatus()

    suspend fun getStatics(param: PtPostStatics) = prApi.getStatics(param)

    suspend fun getRecordByTeams(param: PtPostTeams) = prApi.getRecordByTeams(param)

    suspend fun getRecordDetail(param: PtGetRecordDetail) = prApi.getRecordDetail(param)

    suspend fun getHistories(param: PtPostTeams) = prApi.getHistories(param)

    suspend fun delHistory(param: PtDelHistory) = prApi.delHistory(param)

    suspend fun getDayPlay(param: PtGetPlay) = prApi.getDayPlay(param)

    suspend fun postNewGame(param: PtPostPlay) = prApi.saveNewGame(param)

    suspend fun delUser(param: PtDelUser) = prApi.delUser(param)

    suspend fun postUser(param: PtPostUser) = prApi.postUser(param)
}