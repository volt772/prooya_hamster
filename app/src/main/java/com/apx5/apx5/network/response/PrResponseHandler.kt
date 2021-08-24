package com.apx5.apx5.network.response

import com.apx5.apx5.constants.PrNetworks
import com.apx5.apx5.network.operation.PrOpsCallBack
import com.apx5.apx5.network.operation.PrOpsError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * PrResponseHandler
 * @desc Response Handler
 * @Deprecated
 */

class PrResponseHandler private constructor() {
    fun <T> handleResponse(call: Call<PrResponse<T>>, serviceCallBack: PrOpsCallBack<T>) {
        call.enqueue(object : Callback<PrResponse<T>> {
            override fun onResponse(call: Call<PrResponse<T>>, response: Response<PrResponse<T>>) {
                var message = response.message()

                when (response.code()) {
                    200 -> { message = PrNetworks.StatusMessage.OK }
                    201 -> { message = PrNetworks.StatusMessage.CREATED }
                    202 -> { message = PrNetworks.StatusMessage.ACCEPTED }
                    203 -> { message = PrNetworks.StatusMessage.NON_AUTH }
                    400 -> { message = PrNetworks.StatusMessage.BAD_REQUEST }
                    401 -> { message = PrNetworks.StatusMessage.UNAUTHORIZED }
                    404 -> { message = PrNetworks.StatusMessage.NOT_FOUND }
                    else -> {
                        serviceCallBack.onFailed(
                            PrOpsError(
                                "" + response.body()?.responseCode,
                                "" + response.body()?.responseMessage
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
