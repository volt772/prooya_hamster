package com.apx5.apx5.network.operation

import com.apx5.apx5.model.ResourcePostStatics
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.network.api.PrApiService
import com.apx5.apx5.network.dto.PrPingDto
import com.apx5.apx5.network.dto.PrStaticsDto
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.network.response.PrResponseHandler
import retrofit2.Call

class PrOps {
    private val api: PrApi = PrApiService.getInstance().getService()

    /**
     * 서버 Ping 검사
     */
    fun checkPing(sc: PrOpsCallBack<PrPingDto>) {
        val call: Call<PrResponse<PrPingDto>> = this.api.appPing()
        PrResponseHandler.getInstance().handleResponse(call, sc)
    }

    /**
     * 통계데이터
     */
    fun getStatics(userEmail: String, sc: PrOpsCallBack<PrStaticsDto>) {
        val resourcePostStatics = ResourcePostStatics(userEmail)
        val call: Call<PrResponse<PrStaticsDto>> = this.api.getStatics(resourcePostStatics)
        PrResponseHandler.getInstance().handleResponse(call, sc)
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