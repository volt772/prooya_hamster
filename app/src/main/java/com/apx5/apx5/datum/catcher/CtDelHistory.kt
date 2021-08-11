package com.apx5.apx5.datum.catcher

import com.apx5.apx5.constants.PrNetworkKeys
import com.google.gson.annotations.SerializedName

/**
 * 경기삭제
 */

data class CtDelHistory(
    @SerializedName(PrNetworkKeys.COUNT)
    val count: Int = 0
)