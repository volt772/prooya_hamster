package com.apx5.apx5.network.operation

import com.apx5.apx5.network.dto.Statics2
import com.apx5.apx5.model.ResourcePostStatics
import com.apx5.apx5.network.api.PrApi
import com.apx5.apx5.network.api.PrApiService
import com.apx5.apx5.network.response.PrResponse
import com.apx5.apx5.network.response.PrResponseHandler
import retrofit2.Call

class PrOps {
    private val api: PrApi = PrApiService.getInstance().getService()

    fun getStatics(userEmail: String, serviceCallBack: PrOpsCallBack<Statics2>) {
        val resourcePostStatics = ResourcePostStatics(userEmail)
        val call: Call<PrResponse<Statics2>> = this.api.getStatics(resourcePostStatics)
        PrResponseHandler.getInstance().handleResponse(call, serviceCallBack)
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