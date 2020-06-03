package com.apx5.apx5.network.api

import com.apx5.apx5.model.*
import com.apx5.apx5.network.dto.*
import com.apx5.apx5.network.response.PrResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * PrApi
 */

interface PrApi {
    /* 서버 사용 검사 */
    @POST(URL_PING)
    fun appPing(): Call<PrResponse<PrPingDto>>

    /* 신규사용자등록 */
    @POST(URL_USER_POST)
    fun postUser(@Body user: ResourcePostUser): Call<PrResponse<PrUserDto>>

    /* 사용자삭제 */
    @POST(URL_USER_DELETE)
    fun delUser(@Body user: ResourceDelUser): Call<PrResponse<PrUserDelDto>>

    /* 요약데이터 */
    @POST(URL_STATICS)
    fun getStatics(@Body statics: ResourcePostStatics): Call<PrResponse<PrStaticsDto>>

    /* 팀 간단데이터 */
    @POST(URL_TEAMS_ALL)
    fun getRecordByTeams(@Body teams: ResourcePostTeams): Call<PrResponse<PrRecordsDto>>

    /* 팀 경기상세*/
    @POST(URL_TEAMS_DETAIL)
    fun getRecordDetail(@Body play: ResourceGetRecordDetail): Call<PrResponse<PrRecordDetailDto>>

    /* 전체 간단데이터 */
    @POST(URL_HISTORIES_ALL)
    fun getHistories(@Body play: ResourcePostTeams): Call<PrResponse<PrHistoriesDto>>

    /* 경기삭제*/
    @POST(URL_HISTORIES_DELETE)
    fun delHistory(@Body play: ResourceDelHistory): Call<PrResponse<PrHistoryDelDto>>

    /* 오늘 내팀 경기저장*/
    @POST(URL_HISTORIES_POST)
    fun saveNewGame(@Body play: ResourcePostPlay): Call<PrResponse<PrNewGameDto>>

    /* 오늘 내팀 경기목록*/
    @POST(URL_SCORES_GET)
    fun getDayPlay(@Body play: ResourceGetPlay): Call<PrResponse<PrGameDto>>


    companion object {
        private const val URL_BASE = "prooya/v1"
        private const val URL_PING = "$URL_BASE/ping"
        private const val URL_USER_POST = "$URL_BASE/users/post"
        private const val URL_USER_DELETE = "$URL_BASE/users/del"
        private const val URL_STATICS = "$URL_BASE/statics"
        private const val URL_TEAMS_ALL = "$URL_BASE/teams/all"
        private const val URL_TEAMS_DETAIL = "$URL_BASE/teams/detail"
        private const val URL_HISTORIES_ALL = "$URL_BASE/histories/all"
        private const val URL_HISTORIES_DELETE = "$URL_BASE/histories/del"
        private const val URL_HISTORIES_POST = "$URL_BASE/histories/post"
        private const val URL_SCORES_GET = "$URL_BASE/scores/get"
    }
}
