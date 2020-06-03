package com.apx5.apx5.network.dto

import com.google.gson.annotations.SerializedName

/**
 * 신규사용자등록
 */

open class PrUserDto {
        @SerializedName("id")
        val id: Int = 0

        @SerializedName("team")
        val team: String = ""
}
