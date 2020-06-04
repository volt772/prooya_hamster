package com.apx5.apx5.datum.catcher

import com.apx5.apx5.constants.PrNetworkKeys
import com.google.gson.annotations.SerializedName

/**
 * 서버 사용 검사
 */

open class CtPing {
    @SerializedName(PrNetworkKeys.STATUS)
    val status: Int = 0
}
