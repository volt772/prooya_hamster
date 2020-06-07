package com.apx5.apx5.datum.catcher

import com.apx5.apx5.constants.PrNetworkKeys
import com.google.gson.annotations.SerializedName

/**
 * 오늘 내팀 경기저장
 */

open class CtPostPlay {
    @SerializedName(PrNetworkKeys.RESULT)
    val result: Int = 0
}
