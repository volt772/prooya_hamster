package com.apx5.apx5.network.response

import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrResponseHandler private constructor() {
    fun <T> handleResponse(call: Call<PrResponse<T>>, serviceCallBack: PrOpsCallBack<T>) {

        call.enqueue(object : Callback<PrResponse<T>> {
            override fun onResponse(call: Call<PrResponse<T>>, response: Response<PrResponse<T>>) {
                var message = response.message()

                when (response.code()) {
                    200 -> { message = "OK" }
                    201 -> { message = "Created" }
                    202 -> { message = "Accepted" }
                    203 -> { message = "Non-Authoritative Information" }
                    400 -> { message = "Bad Request" }
                    401 -> { message = "Unauthorized" }
                    404 -> { message = "Not Found" }
                    else -> {
                        serviceCallBack.onFailed(
                            PrOpsError(
                                "" + response.body()!!.responseCode,
                                "" + response.body()!!.responseMessage
                            )
                        )
                    }
                }
                try {
                    serviceCallBack.onSuccess(
                        response.code(),
                        message, response.body()
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    serviceCallBack.onFailed(PrOpsError("" + response.code(), "" + e.message))
                }
            }

            override fun onFailure(call: Call<PrResponse<T>>, t: Throwable) {
                serviceCallBack.onFailed(PrOpsError(t.localizedMessage, t.message))
            }
        })
    }

    companion object {
        private var instance: PrResponseHandler? = null

        @Synchronized
        fun getInstance(): PrResponseHandler {
            if (instance == null)
                instance = PrResponseHandler()
            return instance as PrResponseHandler
        }
    }
}
