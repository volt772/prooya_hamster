package com.apx5.domain.repository

import com.apx5.domain.dto.ServerStatusVO

interface PrRepository2 {
    suspend fun getServerStatus(): ServerStatusVO
}
//class PrRepository @Inject constructor(private val prApi: PrApiService) {
//
//    suspend fun getServerStatus() = prApi.getServerStatus()
//
//    suspend fun getStatics(param: PtPostStatics) = prApi.getStatics(param)
//
//    suspend fun getRecordByTeams(param: PtPostTeams) = prApi.getRecordByTeams(param)
//
//    suspend fun getRecordDetail(param: PtGetRecordDetail) = prApi.getRecordDetail(param)
//
//    suspend fun getHistories(param: PtPostTeams) = prApi.getHistories(param)
//
//    suspend fun getPagingHistories(param: PtPostTeams, page: Int, size: Int) = prApi.getPagingHistories(param, page, size)
//
//    suspend fun delHistory(param: PtDelHistory) = prApi.delHistory(param)
//
//    suspend fun getDayPlay(param: PtGetPlay) = prApi.getDayPlay(param)
//
//    suspend fun postNewGame(param: PtPostPlay) = prApi.saveNewGame(param)
//
//    suspend fun delUser(param: PtDelUser) = prApi.delUser(param)
//
//    suspend fun postUser(param: PtPostUser) = prApi.postUser(param)
//}
