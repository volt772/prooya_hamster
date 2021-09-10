package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.google.gson.annotations.SerializedName

/**
 * 사용자삭제
 */

data class UserDelResp(
    @SerializedName(PrNetworkKeys.COUNT)
    val count: Int = 0
)
