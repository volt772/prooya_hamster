package com.apx5.domain.dto

import com.apx5.domain.PrNetworkKeys
import com.apx5.domain.model.PrDelUser
import com.google.gson.annotations.SerializedName

/**
 * UserDelDto
 * @desc 사용자삭제
 */

data class UserDelDto(

    @SerializedName(PrNetworkKeys.COUNT)
    val _count: Int = 0

) : PrDelUser {

    override val count: Int
        get() = _count
}