package com.apx5.data.response

import com.apx5.domain.PrNetworkKeys
import com.google.gson.annotations.SerializedName

/**
 * PrResponse
 * @desc Response
 */
class PrResponse<E> {
    @SerializedName(PrNetworkKeys.DATA)
    var data: E? = null
}
