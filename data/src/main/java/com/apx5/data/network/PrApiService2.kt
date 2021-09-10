package com.apx5.data.network

import com.apx5.data.response.*
import com.apx5.domain.param.*
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface PrApiService2 {
    /* 서버 사용 검사 */
    @POST(URL_PING)
    suspend fun getServerStatus(): PrResponse2<ServerStatusResp>

    /* 요약데이터 */
    @POST(URL_STATICS)
    suspend fun getStatics(@Body statics: StaticsParam): PrResponse2<StaticsResp>

    /* 팀 간단데이터 */
    @POST(URL_TEAMS_ALL)
    suspend fun getRecordByTeams(@Body teamSummary: TeamSummaryParam): PrResponse2<TeamSummaryResp>

    /* 팀 경기상세*/
    @POST(URL_TEAMS_DETAIL)
    suspend fun getRecordDetail(@Body teamDetail: TeamDetailParam): PrResponse2<TeamDetailResp>

    /* 전체 간단데이터 */
    @POST(URL_HISTORIES_ALL)
    suspend fun getHistories(@Body history: HistoriesParam): PrResponse2<HistoriesResp>

    /* 전체 Paging데이터 */
    @POST(URL_HISTORIES_ALL)
    suspend fun getPagingHistories(@Body history: HistoriesParam, @Query("page") page: Int, @Query("size") size: Int): PrPagingResponse<HistoriesResp>

    /* 경기삭제*/
    @POST(URL_HISTORIES_DELETE)
    suspend fun delHistory(@Body historyDelete: HistoryDelParam): PrResponse2<HistoryDelResp>

    /* 오늘 내팀 경기목록*/
    @POST(URL_SCORES_GET)
    suspend fun getDayPlay(@Body game: GameParam): PrResponse2<GameResp>

    /* 오늘 내팀 경기저장*/
    @POST(URL_HISTORIES_POST)
    suspend fun saveNewGame(@Body gameSave: GameSaveParam): PrResponse2<GameSaveResp>

    /* 사용자삭제 */
    @POST(URL_USER_DELETE)
    suspend fun delUser(@Body userDelete: UserDelParam): PrResponse2<UserDelResp>

    /* 신규사용자등록 */
    @POST(URL_USER_POST)
    suspend fun postUser(@Body userRegister: UserRegisterParam): PrResponse2<UserRegisterResp>

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