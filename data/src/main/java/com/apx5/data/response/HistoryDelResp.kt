package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.google.gson.annotations.SerializedName

/**
 * HistoryDelResp
 * @desc 경기삭제
 */

data class HistoryDelResp(
    @SerializedName(PrNetworkKeys.COUNT)
    val count: Int = 0
)
