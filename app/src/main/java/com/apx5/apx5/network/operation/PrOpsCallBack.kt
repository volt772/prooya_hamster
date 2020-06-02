package com.apx5.apx5.network.operation

import com.apx5.apx5.network.response.PrResponse

interface PrOpsCallBack<T> {
    fun onSuccess(responseCode:Int,responseMessage:String, responseBody: PrResponse<T>?)
    fun onFailed(errorData: PrOpsError)
}

