package com.apx5.apx5.network.api

import com.apx5.apx5.datum.catcher.*
import com.apx5.apx5.datum.pitcher.*
import com.apx5.apx5.network.response.MkResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * PrApi
 */

interface PrApiService {
    /* 서버 사용 검사 */
    @POST(URL_PING)
    suspend fun getServerStatus(): MkResponse<CtPing>

    /* 요약데이터 */
    @POST(URL_STATICS)
    suspend fun getStatics(@Body statics: PtPostStatics): MkResponse<CtPostStatics>

    /* 팀 간단데이터 */
    @POST(URL_TEAMS_ALL)
    suspend fun getRecordByTeams(@Body teams: PtPostTeams): MkResponse<CtPostTeams>

    /* 팀 경기상세*/
    @POST(URL_TEAMS_DETAIL)
    suspend fun getRecordDetail(@Body play: PtGetRecordDetail): MkResponse<CtGetRecordDetail>

    /* 전체 간단데이터 */
    @POST(URL_HISTORIES_ALL)
    suspend fun getHistories(@Body play: PtPostTeams): MkResponse<CtHistories>

    /* 경기삭제*/
    @POST(URL_HISTORIES_DELETE)
    suspend fun delHistory(@Body play: PtDelHistory): MkResponse<CtDelHistory>

    /* 오늘 내팀 경기목록*/
    @POST(URL_SCORES_GET)
    suspend fun getDayPlay(@Body play: PtGetPlay): MkResponse<CtGetPlay>

    /* 오늘 내팀 경기저장*/
    @POST(URL_HISTORIES_POST)
    suspend fun saveNewGame(@Body play: PtPostPlay): MkResponse<CtPostPlay>

    /* 사용자삭제 */
    @POST(URL_USER_DELETE)
    suspend fun delUser(@Body user: PtDelUser): MkResponse<CtDelUser>

    /* 신규사용자등록 */
    @POST(URL_USER_POST)
    suspend fun postUser(@Body user: PtPostUser): MkResponse<CtPostUser>


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
