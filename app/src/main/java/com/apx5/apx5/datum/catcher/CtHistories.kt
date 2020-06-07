package com.apx5.apx5.datum.catcher

import com.apx5.apx5.constants.PrNetworkKeys
import com.apx5.apx5.datum.ops.OpsHistories
import com.google.gson.annotations.SerializedName

/**
 * 전체 간단데이터
 */

open class CtHistories {
        @SerializedName(PrNetworkKeys.GAMES)
        val games: List<OpsHistories> = emptyList()
}