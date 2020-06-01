package com.apx5.apx5.network

import com.apx5.apx5.model.*
import com.apx5.apx5.remote.*
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * RemoteService
 */

interface PrApi {
    /* 서버 사용 검사 */
    @POST(URL_PING)
    fun appPing(): Observable<Ping>

    /* 신규사용자등록 */
    @POST(URL_USER_POST)
    fun postUser(@Body user: ResourcePostUser): Observable<PostUser>

    /* 사용자삭제 */
    @POST(URL_USER_DELETE)
    fun delUser(@Body user: ResourceDelUser): Observable<DelUser>

    /* 요약데이터 */
    @POST(URL_STATICS)
    fun getStatics(@Body statics: ResourcePostStatics): Observable<Statics>

    /* 팀 간단데이터 */
    @POST(URL_TEAMS_ALL)
    fun getTeams(@Body teams: ResourcePostTeams): Observable<TeamsSummary>

    /* 팀 경기상세*/
    @POST(URL_TEAMS_DETAIL)
    fun getRecordDetail(@Body play: ResourceGetRecordDetail): Observable<TeamDetail>

    /* 전체 간단데이터 */
    @POST(URL_HISTORIES_ALL)
    fun getHistories(@Body play: ResourcePostTeams): Observable<Histories>

    /* 경기삭제*/
    @POST(URL_HISTORIES_DELETE)
    fun delHistory(@Body play: ResourceDelHistory): Observable<DelPlay>

    /* 오늘 내팀 경기*/
    @POST(URL_HISTORIES_POST)
    fun saveNewPlay(@Body play: ResourcePostPlay): Observable<NewPlay>

    /* 오늘 내팀 경기*/
    @POST(URL_SCORES_GET)
    fun getDayPlay(@Body play: ResourceGetPlay): Observable<Plays>


    /**
     * Object Lists
     */

    /* Object - Ping*/
    class Ping(val res: Boolean)

    /* Object - 사용자 등록*/
    class PostUser(val res: UserItem) {
        inner class UserItem(
            val id: Int,
            val team: String
        )
    }

    /* Object - 사용자 삭제*/
    class DelUser(val res: Int)

    /* Object - 경기 삭제*/
    class DelPlay(val res: Int)

    /* Object - 요약데이터*/
    class Statics(val res: StaticsItem) {
        inner class StaticsItem (
            val team: String,
            val allStatics: RemoteAllStatics,
            val seasonStatics: RemoteSeasonStatics,
            val recentPlays: List<RemoteRecentPlay>
        )
    }

    /* Object - 요약데이터*/
    class TeamsSummary(val res: TeamsSummaryItem) {
        inner class TeamsSummaryItem(
            val teams: List<RemoteTeamRecords>,
            val summary: RemoteTeamSummary
        )
    }

    /* Object - 오늘 내팀 경기*/
    class Plays(val res: List<RemoteDailyPlay>)

    /* Object - 새경기저장*/
    class NewPlay(val res: Int)

    /* Object - 기록 상세*/
    class TeamDetail(val plays: List<RemoteTeamDetail>)

    /* Object - 기록 전체*/
    class Histories(val histories: List<RemoteHistories>)

    companion object {
        private const val URL_BASE = "prooya/v1"
        private const val URL_PING = "${URL_BASE}/ping"
        private const val URL_USER_POST = "${URL_BASE}/users/post"
        private const val URL_USER_DELETE = "${URL_BASE}/users/del"
        private const val URL_STATICS = "${URL_BASE}/statics"
        private const val URL_TEAMS_ALL = "${URL_BASE}/teams/all"
        private const val URL_TEAMS_DETAIL = "${URL_BASE}/teams/detail"
        private const val URL_HISTORIES_ALL = "${URL_BASE}/histories/all"
        private const val URL_HISTORIES_DELETE = "${URL_BASE}/histories/del"
        private const val URL_HISTORIES_POST = "${URL_BASE}/histories/post"
        private const val URL_SCORES_GET = "${URL_BASE}/scores/get"
    }
}
