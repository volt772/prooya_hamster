package com.apx5.apx5.network.response

import com.apx5.apx5.constants.PrNetworks
import com.google.gson.annotations.SerializedName

/**
 * PrResponse
 * @Param E
 * @desc 데이터 Response
 */

class MkResponse<E> {
    @SerializedName(PrNetworks.DATA)
    var data: E? = null
}