package com.apx5.apx5.datum.catcher

import com.apx5.apx5.constants.PrNetworkKeys
import com.google.gson.annotations.SerializedName

/**
 * 신규사용자등록
 */

open class CtPostUser {
    @SerializedName(PrNetworkKeys.ID)
    val id: Int = 0

    @SerializedName(PrNetworkKeys.TEAM)
    val team: String = ""
}
