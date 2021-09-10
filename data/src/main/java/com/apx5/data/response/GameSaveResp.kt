package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.google.gson.annotations.SerializedName

/**
 * 오늘 내팀 경기저장
 */

data class GameSaveResp(
    @SerializedName(PrNetworkKeys.RESULT)
    val result: Int = 0
)
