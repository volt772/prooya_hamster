package com.apx5.apx5.network.dto

import com.google.gson.annotations.SerializedName

/**
 * 서버 사용 검사
 */

open class PrPingDto {
    @SerializedName("status")
    val status: Int = 0
}
