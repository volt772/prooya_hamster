package com.apx5.data.response

import com.google.gson.annotations.SerializedName


data class ServerStatusDO(
    @SerializedName("status")
    val status: Int
)
