package com.apx5.apx5.network.api

import com.apx5.apx5.datum.pitcher.*
import com.apx5.apx5.datum.catcher.*
import com.apx5.apx5.network.response.MkResponse
import com.apx5.apx5.network.response.PrResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * PrApi
 */

interface PrApi {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 서버 사용 검사 */
    @POST(URL_PING)
    suspend fun getServerStatus(): MkResponse<CtPing>

    /* 요약데이터 */
    @POST(URL_STATICS)
    suspend fun getStatics2(@Body statics: PtPostStatics): MkResponse<CtPostStatics>

    /* 팀 간단데이터 */
    @POST(URL_TEAMS_ALL)
    suspend fun getRecordByTeams2(@Body teams: PtPostTeams): MkResponse<CtPostTeams>

    /* 팀 경기상세*/
    @POST(URL_TEAMS_DETAIL)
    suspend fun getRecordDetail2(@Body play: PtGetRecordDetail): MkResponse<CtGetRecordDetail>

    /* 전체 간단데이터 */
    @POST(URL_HISTORIES_ALL)
    suspend fun getHistories2(@Body play: PtPostTeams): MkResponse<CtHistories>

    /* 경기삭제*/
    @POST(URL_HISTORIES_DELETE)
    suspend fun delHistory2(@Body play: PtDelHistory): MkResponse<CtDelHistory>

    /* 오늘 내팀 경기목록*/
    @POST(URL_SCORES_GET)
    suspend fun getDayPlay2(@Body play: PtGetPlay): MkResponse<CtGetPlay>

    /* 오늘 내팀 경기저장*/
    @POST(URL_HISTORIES_POST)
    suspend fun saveNewGame2(@Body play: PtPostPlay): MkResponse<CtPostPlay>
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /* 서버 사용 검사 */
    @POST(URL_PING)
    fun appPing(): Call<PrResponse<CtPing>>

    /* 신규사용자등록 */
    @POST(URL_USER_POST)
    fun postUser(@Body user: PtPostUser): Call<PrResponse<CtPostUser>>

    /* 사용자삭제 */
    @POST(URL_USER_DELETE)
    fun delUser(@Body user: PtDelUser): Call<PrResponse<CtDelUser>>

    /* 요약데이터 */
    @POST(URL_STATICS)
    fun getStatics(@Body statics: PtPostStatics): Call<PrResponse<CtPostStatics>>

    /* 팀 간단데이터 */
    @POST(URL_TEAMS_ALL)
    fun getRecordByTeams(@Body teams: PtPostTeams): Call<PrResponse<CtPostTeams>>

    /* 팀 경기상세*/
    @POST(URL_TEAMS_DETAIL)
    fun getRecordDetail(@Body play: PtGetRecordDetail): Call<PrResponse<CtGetRecordDetail>>

    /* 전체 간단데이터 */
    @POST(URL_HISTORIES_ALL)
    fun getHistories(@Body play: PtPostTeams): Call<PrResponse<CtHistories>>

    /* 경기삭제*/
    @POST(URL_HISTORIES_DELETE)
    fun delHistory(@Body play: PtDelHistory): Call<PrResponse<CtDelHistory>>

    /* 오늘 내팀 경기저장*/
    @POST(URL_HISTORIES_POST)
    fun saveNewGame(@Body play: PtPostPlay): Call<PrResponse<CtPostPlay>>

    /* 오늘 내팀 경기목록*/
    @POST(URL_SCORES_GET)
    fun getDayPlay(@Body play: PtGetPlay): Call<PrResponse<CtGetPlay>>


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
