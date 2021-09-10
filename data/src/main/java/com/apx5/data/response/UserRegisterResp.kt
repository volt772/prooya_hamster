package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.google.gson.annotations.SerializedName

/**
 * 신규사용자등록
 */

data class UserRegisterResp(
    @SerializedName(PrNetworkKeys.ID)
    val id: Int = 0,

    @SerializedName(PrNetworkKeys.TEAM)
    val team: String = ""
)