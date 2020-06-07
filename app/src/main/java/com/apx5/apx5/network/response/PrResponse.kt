package com.apx5.apx5.network.response

import com.apx5.apx5.constants.PrNetworks
import com.google.gson.annotations.SerializedName

/**
 * PrResponse
 * @Param E
 * @desc 데이터 Response
 */

class PrResponse<E> {
    @SerializedName(PrNetworks.RESPONSE_CODE)
    var responseCode: Int = 0

    @SerializedName(PrNetworks.RESPONSE_MESSAGE)
    var responseMessage: String?= null

    @SerializedName(PrNetworks.DATA)
    var data: E? = null
}