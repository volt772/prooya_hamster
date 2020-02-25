package com.apx5.apx5.model

import com.apx5.apx5.remote.RemoteDailyPlay
import com.apx5.apx5.remote.RemoteHistories
import com.apx5.apx5.remote.RemoteTeamDetail
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable
import java.util.*

/**
 * RemoteService
 */

interface RemoteService {
    /* 서버 사용 검사 */
    @POST("prooya/v1/ping")
    fun appPing(): Observable<Ping>

    /* 신규사용자등록 */
    @POST("prooya/v1/users/post")
    fun postUser(@Body user: ResourcePostUser): Observable<PostUser>

    /* 사용자삭제 */
    @POST("prooya/v1/users/del")
    fun delUser(@Body user: ResourceDelUser): Observable<DelUser>

    /* 요약데이터 */
    @POST("prooya/v1/statics")
    fun getStatics(@Body statics: ResourcePostStatics): Observable<Statics>

    /* 팀 간단데이터 */
    @POST("prooya/v1/teams/all")
    fun getTeams(@Body teams: ResourcePostTeams): Observable<TeamsSummary>

    /* 팀 경기상세*/
    @POST("prooya/v1/teams/detail")
    fun getRecordDetail(@Body play: ResourceGetRecordDetail): Observable<TeamDetail>

    /* 전체 간단데이터 */
    @POST("prooya/v1/histories/all")
    fun getHistories(@Body play: ResourcePostTeams): Observable<Histories>

    /* 경기삭제*/
    @POST("prooya/v1/histories/del")
    fun delHistory(@Body play: ResourceDelHistory): Observable<DelPlay>

    /* 오늘 내팀 경기*/
    @POST("prooya/v1/histories/post")
    fun saveNewPlay(@Body play: ResourcePostPlay): Observable<NewPlay>

    /* 오늘 내팀 경기*/
    @POST("prooya/v1/scores/get")
    fun getDayPlay(@Body play: ResourceGetPlay): Observable<Plays>


    /**
     * Object Lists
     */

    /* Object - Ping*/
    class Ping(val res: Boolean)

    /* Object - 사용자 등록*/
    class PostUser(val res: UserItem) {
        inner class UserItem(val id: Int, val team: String)
    }

    /* Object - 사용자 삭제*/
    class DelUser(val res: Int)

    /* Object - 경기 삭제*/
    class DelPlay(val res: Int)

    /* Object - 요약데이터*/
    class Statics(val res: StaticsItem) {
        inner class StaticsItem(
            var allStatics: HashMap<String, Int>,
            var seasonStatics: HashMap<String, Int>,
            var recentPlays: List<HashMap<String, String>>)
    }

    /* Object - 요약데이터*/
    class TeamsSummary(val res: TeamsSummaryItem) {
        inner class TeamsSummaryItem(
            var teams: List<HashMap<String, String>>,
            var summary: HashMap<String, Int>)
    }

    /* Object - 오늘 내팀 경기*/
    class Plays(val res: List<RemoteDailyPlay>)

    /* Object - 새경기저장*/
    class NewPlay(val res: Int)

    /* Object - 기록 상세*/
    class TeamDetail(val plays: List<RemoteTeamDetail>)

    /* Object - 기록 전체*/
    class Histories(val histories: List<RemoteHistories>)
}
