package com.apx5.apx5.network.operation

import com.apx5.apx5.datum.pitcher.*
import com.apx5.apx5.datum.catcher.*
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.network.api.PrApiService
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.network.response.PrResponseHandler
import retrofit2.Call

/**
 * PrOps
 * @desc 데이터 Operations
 */

class PrOps {
    private val api: PrApi = PrApiService.getInstance().getService()

    private fun<T> handleResponse(call: Call<PrResponse<T>>, operation: PrOpsCallBack<T>) {
        PrResponseHandler.getInstance().handleResponse(call, operation)
    }

    /**
     * 서버 Ping 검사
     */
    fun checkPing(operation: PrOpsCallBack<CtPing>) {
//        handleResponse(this.api.appPing(), operation)
    }

    /**
     * 사용자 정보변경 (= 팀변경)
     */
    fun modifyUserInfo(request: PtPostUser, operation: PrOpsCallBack<CtPostUser>) {
        handleResponse(this.api.postUser(request), operation)
    }

    /**
     * 사용자 삭제
     */
    fun deleteUserInfo(request: PtDelUser, operation: PrOpsCallBack<CtDelUser>) {
        handleResponse(this.api.delUser(request), operation)
    }

    /**
     * 통계데이터
     */
    fun getStatics(request: PtPostStatics, operation: PrOpsCallBack<CtPostStatics>) {
        handleResponse(this.api.getStatics(request), operation)
    }

    /**
     * 팀 상세 데이터 (전체)
     */
    fun getRecordByTeams(request: PtPostTeams, operation: PrOpsCallBack<CtPostTeams>) {
        handleResponse(this.api.getRecordByTeams(request), operation)
    }

    /**
     * 팀 상세 데이터 (특정팀)
     */
    fun getRecordDetails(request: PtGetRecordDetail, operation: PrOpsCallBack<CtGetRecordDetail>) {
        handleResponse(this.api.getRecordDetail(request), operation)
    }

    /**
     * 경기목록
     */
    fun getHistories(request: PtPostTeams, operation: PrOpsCallBack<CtHistories>) {
        handleResponse(this.api.getHistories(request), operation)
    }

    /**
     * 경기삭제
     */
    fun deleteHistory(request: PtDelHistory, operation: PrOpsCallBack<CtDelHistory>) {
        handleResponse(this.api.delHistory(request), operation)
    }

    /**
     * 오늘 경기 저장
     */
    fun postGame(request: PtPostPlay, operation: PrOpsCallBack<CtPostPlay>) {
        handleResponse(this.api.saveNewGame(request), operation)
    }

    /**
     * 오늘 경기 로드
     */
    fun loadTodayGame(request: PtGetPlay, operation: PrOpsCallBack<CtGetPlay>) {
        handleResponse(this.api.getDayPlay(request), operation)
    }


    companion object {
        private var instance: PrOps? = null

        fun getInstance(): PrOps {
            if (instance == null) {
                instance = PrOps()
            }
            return instance as PrOps
        }
    }
}