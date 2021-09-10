package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.google.gson.annotations.SerializedName


data class ServerStatusResp(
    @SerializedName(PrNetworkKeys.STATUS)
    val status: Int
)
