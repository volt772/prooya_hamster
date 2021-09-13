package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.google.gson.annotations.SerializedName

/**
 * ServerStatusResp
 * @desc Response : 서버상태
 */
data class ServerStatusResp(
    @SerializedName(PrNetworkKeys.STATUS)
    val status: Int
)
