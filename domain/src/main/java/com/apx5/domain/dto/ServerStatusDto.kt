package com.apx5.domain.dto

import com.apx5.domain.PrNetworkKeys
import com.apx5.domain.model.PrServerStatus
import com.google.gson.annotations.SerializedName

/**
 * ServerStatusDto
 * @desc 서버상태
 */
data class ServerStatusDto(

    @SerializedName(PrNetworkKeys.STATUS)
    val _status: Int = 0

) : PrServerStatus {

    override val status: Int
        get() = _status
}