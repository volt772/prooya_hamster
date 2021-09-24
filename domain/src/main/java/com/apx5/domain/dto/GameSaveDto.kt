package com.apx5.domain.dto

import com.apx5.domain.PrNetworkKeys
import com.apx5.domain.model.PrGameSave
import com.google.gson.annotations.SerializedName

/**
 * GameSaveDto
 * @desc 오늘 내팀 경기저장
 */

data class GameSaveDto(

    @SerializedName(PrNetworkKeys.RESULT)
    val _result: Int = 0

) : PrGameSave {

    override val result: Int
        get() = _result
}