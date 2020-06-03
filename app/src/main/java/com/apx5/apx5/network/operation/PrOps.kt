package com.apx5.apx5.network.operation

import com.apx5.apx5.model.ResourceDelUser
import com.apx5.apx5.model.ResourcePostStatics
import com.apx5.apx5.model.ResourcePostUser
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.network.api.PrApiService
import com.apx5.apx5.network.dto.PrPingDto
import com.apx5.apx5.network.dto.PrStaticsDto
import com.apx5.apx5.network.dto.PrUserDelDto
import com.apx5.apx5.network.dto.PrUserDto
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