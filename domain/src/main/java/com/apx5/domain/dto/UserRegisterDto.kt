package com.apx5.domain.dto

import com.apx5.domain.PrNetworkKeys
import com.apx5.domain.model.PrUserRegister
import com.google.gson.annotations.SerializedName

/**
 * UserRegisterDto
 * @desc 신규사용자등록
 */

data class UserRegisterDto(

    @SerializedName(PrNetworkKeys.ID)
    val _id: Int = 0,

    @SerializedName(PrNetworkKeys.TEAM)
    val _team: String = ""

) : PrUserRegister {

    override val id: Int
        get() = _id

    override val team: String
        get() = _team
}
