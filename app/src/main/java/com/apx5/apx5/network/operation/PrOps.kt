package com.apx5.apx5.network.operation

import com.apx5.apx5.model.*
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.network.api.PrApiService
import com.apx5.apx5.network.dto.*
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.network.response.PrResponseHandler
import retrofit2.Call

class PrOps {
    private val api: PrApi = PrApiService.getInstance().getService()

    private fun<T> handleResponse(call: Call<PrResponse<T>>, operation: PrOpsCallBack<T>) {
        PrResponseHandler.getInstance().handleResponse(call, operation)
    }

    /**
     * 서버 Ping 검사
     */
    fun checkPing(operation: PrOpsCallBack<PrPingDto>) {
        handleResponse(this.api.appPing(), operation)
    }

    /**
     * 사용자 정보변경 (= 팀변경)
     */
    fun modifyUserInfo(request: ResourcePostUser, operation: PrOpsCallBack<PrUserDto>) {
        handleResponse(this.api.postUser(request), operation)
    }

    /**
     * 사용자 삭제
     */
    fun deleteUserInfo(request: ResourceDelUser, operation: PrOpsCallBack<PrUserDelDto>) {
        handleResponse(this.api.delUser(request), operation)
    }

    /**
     * 통계데이터
     */
    fun getStatics(request: ResourcePostStatics, operation: PrOpsCallBack<PrStaticsDto>) {
        handleResponse(this.api.getStatics(request), operation)
    }

    /**
     * 팀 상세 데이터 (전체)
     */
    fun getRecordByTeams(request: ResourcePostTeams, operation: PrOpsCallBack<PrRecordsDto>) {
        handleResponse(this.api.getRecordByTeams(request), operation)
    }

    /**
     * 팀 상세 데이터 (특정팀)
     */
    fun getRecordDetails(request: ResourceGetRecordDetail, operation: PrOpsCallBack<PrRecordDetailDto>) {
        handleResponse(this.api.getRecordDetail(request), operation)
    }

    /**
     * 경기목록
     */
    fun getHistories(request: ResourcePostTeams, operation: PrOpsCallBack<PrHistoriesDto>) {
        handleResponse(this.api.getHistories(request), operation)
    }

    /**
     * 경기삭제
     */
    fun deleteHistory(request: ResourceDelHistory, operation: PrOpsCallBack<PrHistoryDelDto>) {
        handleResponse(this.api.delHistory(request), operation)
    }

    /**
     * 오늘 경기 저장
     */
    fun postGame(request: ResourcePostPlay, operation: PrOpsCallBack<PrNewGameDto>) {
        handleResponse(this.api.saveNewGame(request), operation)
    }

    /**
     * 오늘 경기 로드
     */
    fun loadTodayGame(request: ResourceGetPlay, operation: PrOpsCallBack<PrGameDto>) {
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